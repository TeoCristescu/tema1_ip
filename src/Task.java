package WebCrawler.tema1_ip.src;

import WebCrawler.tema1_ip.src.DirectoryNotFoundException;

import java.io.IOException;

public interface Task {
    public void execute() throws DirectoryNotFoundException, IOException;
}
