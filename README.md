# GXT (for GTA III)

This is a Java library that provides class for working with *.gxt files (work only with GTA III, for now).

GTA III's GXT files are identical to those of GTA 2, except that they do not have headers – they start with the TKEY
block instead. There is also no special treatment of characters starting with 0x21 in TDAT entries.

The Xbox version of Vice City uses this format as well.

If you are interested you can read more about GXT format at: https://gtamods.com/wiki/GXT

## Demo Code

```java
class GXTDemo {

    public static void main(String[] args) throws IOException {
        GXT gxt = GXT.from(Paths.get("american.gxt"));
        Map<String, String> map = gxt.toMap(); // Convert to map for better usage
        map.put("DUDE_1", "Mario"); // Update map entries
        gxt = GXT.from(map); // Convert map back to GXT
        gxt.writeTo(Paths.get("output.gxt")); // Write GXT to file
    }

}
```

## Format Illustrated

![illustrated format](https://github.com/geo-gta3/gxt/blob/main/gxt%20illustrated.png?raw=true)
