package earth.storage

import ratpack.exec.Promise
import java.nio.file.Path

interface StorageService {

  Promise<Path> store(InputStream is, Path at)
}
