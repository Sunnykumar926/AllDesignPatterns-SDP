# Egger Intialization Singleton

- Object is created before main() starts 
- During static initialization phase 
- No locking required
- No double Checking logic
- No race condition during access

👉 It is thread-safe in normal cases
👉 It is simpler than double-checked locking
👉 No instruction reordering issue during `getInstance()`

### But it still has problem 

#### 1️⃣ Static Initialization Order Fiasco

- Across multiple .cpp files, the order of static initialization is undefined. If some other global object in another file calls:
    - `Singleton::getInstance()`
- then you get undefined behavior 
- Access to uninitialized pointer

#### 2️⃣ Memory Leak

- `new Singleton();` is never deleted because destructro is never called. so, there is not proper memory cleanup which is bad practice in long running systems
