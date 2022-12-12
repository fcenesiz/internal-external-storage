## internal-storage

Files are saved on device's internal storage

By default, the saved files are private to the application
- Other apps cannot access the files
- User is also not allowed to access the files

When the app is uninstalled
- The saved files related to the app are also removed from device

Important Methods
- ``openFileOutput(fileName: String, mode: Int)`` 
it creates a new file if the file does not exist
    - int mode
        - ``MODE_PRIVATE`` (default) makes the file private to the application
        - ``MODE_APPEND`` if the file already exists then it writes the data at the end of the file


- ``write(dataBytes: byte[])``
write the data in the form of bytes to a file


- ``close()``
close the ``FileOutputStream``

also internal cache storage is same 
- to access cache file, need to use ``getCacheDir()`` method
- Limited for ``1 MB``
