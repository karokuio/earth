package earth.util

import org.zeroturnaround.zip.ZipUtil

/**
 * Zip utility functions
 *
 * @since 0.1.0
 */
class Zip {

  /**
   * Gets the content of a given zip entry by its name
   *
   * @param is the zip {@link InputStream}
   * @param name the name of the zip entry
   * @return the content of the zip entry
   * @since 0.1.0
   */
  static String toText(InputStream is, String name) {
    byte[] textBytes = ZipUtil.unpackEntry(is, name)

    return textBytes ? new String(textBytes) : ""
  }
}
