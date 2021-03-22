package mediathek.util.tools.affinity

interface IAffinity {
    fun setDesiredCpuAffinity(numCpus: Int)
}