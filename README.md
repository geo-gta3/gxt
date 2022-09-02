# GXT (for GTA III) 
#### Beware: This code requires abstraction and optimization!

This is a Java library that provides class for working with *.gxt files (work only with GTA III, for now).

GTA III's GXT files are identical to those of GTA 2, except that they do not have headers – they start with the TKEY
block instead. There is also no special treatment of characters starting with 0x21 in TDAT entries.

The Xbox version of Vice City uses this format as well.

If you are interested you can read more about GXT format at: https://gtamods.com/wiki/GXT

## Demo Code

```java
class GXTDemo {

    public static void main(String[] args) throws IOException {
        // Parsing GXT file is easy. Just use GXTParser class' parse method.
        GXT gxt = GXTParser.parse(Paths.get("input.gxt"));

        // Creating *.gxt file from GXT class is straightforward. 
        // Call the "writeTo" method and that's it.  
        gxt.writeTo(Paths.get("output.gxt"));
    }

}
```

## Format Illustrated

![illustrated format](https://github.com/geo-gta3/gxt/blob/main/gxt%20illustrated.png?raw=true)
