# Issue in Double Checked Locking

- DCL is problematic because object creation is not atomic and can be reorder by the compiler or CPU. because 
    - `instance = new Singleton();`
        - it actually does roughly 3 steps:
            1. Allocate memory
            2. Call constructor
            3. Assign pointer to `instance`
        - But due to **compiler + CPU optimizations**, steps can be reordered like:
            1. Allocate memory
            2. Assing pointer to `instance`
            3. Call constructor
        
    - And due to this a critical situation arise that is:
    - **Thread A**
        - Enters lock
        - starts creating singleton
        - `instance` pointer get assigned 
        - but constructor not finished yet
    - Then **Thread B**
        - Calls `getInstance()`
        - First check `instance!=nullptr`
        - Skips locking 
        - Returns **partially constructed object**

