# GXT (for GTA III)

This is a Java library that provides class for working with *.gxt files (works only with GTA III, for now).

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

Just include in your Maven project the following dependency:

```xml
<!-- GXT -->
<dependency>
    <groupId>ge.vakho</groupId>
    <artifactId>gxt</artifactId>
    <version>1.0.1</version>
</dependency>
```

Don't forget to include the repository information too. The package itself is not on Maven central repository.

```xml
<!-- Repository of GXT -->
<repository>
    <id>github</id>
    <name>GitHub Packages</name>
    <url>https://public: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@maven.pkg.github.com/geo-gta3/gxt</url>
</repository>
```

## Format Illustrated

![illustrated format](https://github.com/geo-gta3/gxt/blob/main/gxt%20illustrated.png?raw=true)
