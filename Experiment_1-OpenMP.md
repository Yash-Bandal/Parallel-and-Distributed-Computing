```bash
 mingw-get install pthreads-w32
```

## Program 1
```c
#include<stdio.h>
#include<omp.h>

int main(){
    omp_set_num_threads(4);

    #pragma omp parallel
    {
        int thread_num  = omp_get_thread_num();
        int num_threads = omp_get_num_threads();

        printf("Hello from Thread - %d out of %d \n", thread_num, num_threads);
    }

    
    return 0;
}


//gcc -fopenmp prog-1.c -o prog-1
//./prog-1

```

<br>

## Program 2
```c
#include <stdio.h>
#include <omp.h>

int main()
{
    int n = 5;
    int A[n], B[n], C[n];

    for (int i = 0; i < n; i++)
    {
        A[i] = i;
        B[i] = 2 * i;
    }

#pragma omp parallel for // no {}
    for (int i = 0; i < n; i++)
    {
        C[i] = A[i] + B[i];
        printf("Thread %d Calculates C[%d]= A[%d] +B[%d] and C[%d] = %d\n", omp_get_thread_num(), i, i, i, i, C[i]);
    }

    for (int i = 0; i < n; i++)
    {
        printf("%d, ", C[i]);
    }

    return 0;
}


// gcc -fopenmp prog-2.c -o prog-2
//./prog-2
```
