package mediathek.util.tools.sender_icon_cache

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader.InvalidCacheLoadException
import com.google.common.cache.LoadingCache
import mediathek.util.messages.SenderIconStyleChangedEvent
import mediathek.util.config.ApplicationConfiguration
import mediathek.util.tools.MessageBus
import mediathek.util.tools.TimerPool
import net.engio.mbassy.listener.Handler
import org.apache.logging.log4j.LogManager
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import javax.swing.ImageIcon

/**
 * This class will load only one instance for all used sender icons.
 */
object MVSenderIconCache {
    private val useLocalIcons = AtomicBoolean(false)
    private val smallSenderCache: LoadingCache<String, Optional<ImageIcon>>
    private val largeSenderCache: LoadingCache<String, Optional<ImageIcon>>
    private val logger = LogManager.getLogger()
    const val CONFIG_USE_LOCAL_SENDER_ICONS = "application.sender_icons.use_local"


    @Handler
    private fun handleSenderIconStyleChangedEvent(e: SenderIconStyleChangedEvent) {
        logger.trace("invalidating caches due to sender icon style change")
        useLocalIcons.set(ApplicationConfiguration.getConfiguration().getBoolean(CONFIG_USE_LOCAL_SENDER_ICONS, false))
        smallSenderCache.invalidateAll()
        largeSenderCache.invalidateAll()
    }

    private fun setupCleanupScheduler() {
        TimerPool.timerPool.scheduleAtFixedRate({
            logger.trace("Cleaning sender icon caches")
            largeSenderCache.cleanUp()
            smallSenderCache.cleanUp()
        }, 5, 5, TimeUnit.MINUTES)
    }

    /**
     * Get the icon for a specific sender.
     *
     * @param sender The name of the supported sender.
     * @param small  large or small icon requested.
     * @return The [javax.swing.ImageIcon] for the sender or null.
     */
    @JvmStatic
    operator fun get(sender: String, small: Boolean): Optional<ImageIcon> {
        return try {
            if (small)
                smallSenderCache[sender]
            else
                largeSenderCache[sender]
        } catch (ex: InvalidCacheLoadException) {
            Optional.empty()
        } catch (ex: ExecutionException) {
            Optional.empty()
        }
    }

    init {
        logger.trace("Initializing sender icon cache...")
        setupCleanupScheduler()

        largeSenderCache = CacheBuilder.newBuilder()
            .expireAfterAccess(2, TimeUnit.HOURS)
            .build(
                SenderIconCacheLoader(
                    SenderIconSize.LARGE,
                    useLocalIcons
                )
            )
        smallSenderCache = CacheBuilder.newBuilder()
            .expireAfterAccess(2, TimeUnit.HOURS)
            .build(
                SenderIconCacheLoader(
                    SenderIconSize.SMALL,
                    useLocalIcons
                )
            )

        MessageBus.messageBus.subscribe(this)
        useLocalIcons.set(ApplicationConfiguration.getConfiguration().getBoolean(CONFIG_USE_LOCAL_SENDER_ICONS, false))
    }
}