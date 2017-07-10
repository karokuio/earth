package earth.util

import org.zeroturnaround.zip.ZipUtil

class Zip {

  static String toText(InputStream is, String name) {
    byte[] textBytes = ZipUtil.unpackEntry(is, name)

    return textBytes ? new String(textBytes) : ""
  }
}
