# GXT (for GTA III)

This is a Kotlin/Java library that provides class for working with *.gxt files (works only with GTA III, for now).

GTA III's GXT files are identical to those of GTA 2, except that they do not have headers – they start with the TKEY
block instead. There is also no special treatment of characters starting with 0x21 in TDAT entries.

The Xbox version of Vice City uses this format as well.

If you are interested you can read more about GXT format at: https://gtamods.com/wiki/GXT

## Demo Code

```java
class GXTDemo {

    public static void main(String[] args) throws IOException {
        GXT gxt = GXT.from(Paths.get("input.gxt"));
        Map<String, String> map = gxt.toMap();  // (1) Convert to map for better usage
        map.put("DUDE_1", "Mario");             // (2) Update map entries
        gxt = GXT.from(map);                    // (3) Convert map back to GXT
        gxt.writeTo(Paths.get("output.gxt"));   // (4) Write GXT to file
    }

}
```

## How to Access the Package

The library is not yet published on some public repository. So, the only way is to download the JAR file and do the
installation by hand.

More info can be found here: https://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html

## Format Illustrated

![illustrated format](https://github.com/geo-gta3/gxt/blob/main/gxt%20illustrated.png?raw=true)
