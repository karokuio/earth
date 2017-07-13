package earth.storage

import ratpack.exec.Promise
import ratpack.exec.Blocking
import java.nio.file.Path
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class LocalStorageService implements StorageService {

  @Override
  Promise<Path> store(final InputStream is, final Path path) {
    return Blocking.get {
      Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING)
      return path
    }
  }
}
