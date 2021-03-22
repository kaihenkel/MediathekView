package mediathek.util.tools;

public interface InputStreamProgressMonitor {
    void progress(long bytesRead, long size);
}
