1. Class Loader: The Class Loader is responsible for bringing Java classes into memory. Whenever a class is used for 
                 the first time, the Class Loader loads its .class file, verifies it for safety, and prepares it for 
                 execution. Java uses a hierarchy of class loaders, such as the Bootstrap loader (for core Java 
                 classes), the Extension loader(for standard Java Extension Directories), and the Application loader 
                 (which loads classes from the project classpath).

2. Runtime Data Areas: The JVM divides memory into different regions that it uses while running a program.
   Heap: This is where all objects and arrays are stored. Since it’s shared among all threads, the Garbage Collector 
         also works mainly here.
   Stack: Each thread gets its own stack. It stores method calls, local variables, and partial results. Stack memory is 
          automatically cleaned up when methods finish.
   Method Area: This area holds class‑level information like field names, method metadata, static variables, and 
                runtime generated code. 
   PC Register: Each thread has a Program Counter register. It keeps track of the instruction that the thread is 
                currently executing.

3. Execution Engine: Once the class is loaded and verified, the Execution Engine runs the bytecode. It uses two main 
                     mechanisms:
   Interpreter: Executes bytecode instruction-by-instruction. It starts quickly but can be slower for repeated 
                operations.
   JIT Compiler: When the JVM detects frequently executed sections of code, the JIT compiler converts those parts into 
                 native machine instructions. This makes execution much faster.

4. JIT Compiler vs Interpreter
   The interpreter reads bytecode line-by-line, so it helps the program start quickly. However, repeated instructions 
   become slow.
   The JIT compiler helps by converting “hot” parts of the code into machine-level instructions. Once compiled, those 
   portions run directly on the CPU, giving much better performance.
   The interpreter helps with quick startup, and JIT helps with long‑term speed.

5. Write Once, Run Anywhere: Java programs are compiled into platform‑independent bytecode. This bytecode does not 
                             depend on the operating system or hardware directly. When the program runs, the JVM on the 
                             target system translates the bytecode into machine instructions suitable for that system.
                             Because of this approach, the same Java program can run on Windows, macOS, Linux, or any 
                             other platform that has a JVM implementation.