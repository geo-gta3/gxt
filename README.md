# GXT (for GTA III)

This is a Java library that provides class for working with *.gxt files (work only with GTA III, for now).

GTA III's GXT files are identical to those of GTA 2, except that they do not have headers – they start with the TKEY block instead. 
There is also no special treatment of characters starting with 0x21 in TDAT entries.

The Xbox version of Vice City uses this format as well.

If you are interested you can read more about GXT format at: https://gtamods.com/wiki/GXT