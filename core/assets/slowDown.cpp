// Turn off optimizations to make sure the busy wait loops do
// not get optimized out!

#include<winnt.h>

HANDLE hThread = OpenProcess(PROCESS_ALL_ACCESS, FALSE, processId); // thread that you want to slow down 
for (;;) { 
  SuspendThread(hThread); // Do this for each process thread

  // Busy wait for pause (possibly small)
  const DWORDLONG pauseFactor=1000; // In cycles
  DWORDLONGtart=__rdtsc();

  while (__rdtsc()-start<pauseFactor)
    ;  

  ResumeThread(hThread); // Do this for each process thread

  // Busy wait for resume
  const DWORDLONG runFactor=1000; // In cycles
  DWORDLONG start=__rdtsc();

  while (__rdtsc()-start<runFactor)
    ;  
}