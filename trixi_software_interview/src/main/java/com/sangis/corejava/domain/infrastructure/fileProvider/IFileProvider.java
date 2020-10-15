package com.sangis.corejava.domain.infrastructure.fileProvider;

import java.io.File;
import java.io.IOException;

public interface IFileProvider {
    File getFile() throws IOException;
}
