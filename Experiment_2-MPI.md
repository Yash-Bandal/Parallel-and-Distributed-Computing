
## MPI Client Server - Revstring application
```c
#include <mpi.h>
#include <stdio.h>
#include <string.h>

int main(int argc, char *argv[])
{
    int rank, size;    // Every MPI process has a unique ID called rank.
    char str[100];     // Buffer to hold string
    MPI_Status status; // MPI status for receive

    // Initialize MPI environment
    MPI_Init(&argc, &argv);

    // Get process ID (rank) and total number of processes
    MPI_Comm_rank(MPI_COMM_WORLD, &rank); // 🌍
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    // Processes 2 (CLient-0 and server-1)
    //  Ensure at least 2 processes are used
    if (size < 2)
    {
        if (rank == 0) //1 process only
            printf("Run with at least 2 processes.\n");
        MPI_Finalize();
        return 0;
    }

    if (rank == 0)
    { // Client process
        printf("CLIENT: Enter string: ");
        fflush(stdout);

        // fgets(str, sizeof(str), stdin);
        // str[strcspn(str, "\n")] = '\0'; // Remove newline

        scanf("%s", str); // Reads input until first whitespace

        // Send string to server (process 1)
        MPI_Send(str, strlen(str) + 1, MPI_CHAR, 1, 0, MPI_COMM_WORLD);

        // Receive reversed string from server
        MPI_Recv(str, 100, MPI_CHAR, 1, 1, MPI_COMM_WORLD, &status);

        // Print reversed string
        printf("CLIENT: Reversed string: %s\n", str);
    }
    else if (rank == 1)
    { // Server process
        // Receive string from client (process 0)
        MPI_Recv(str, 100, MPI_CHAR, 0, 0, MPI_COMM_WORLD, &status);
        printf("SERVER: Received string: %s\n", str);

        // Reverse the string
        int len = strlen(str);
        for (int i = 0; i < len / 2; i++)
        {
            char temp = str[i];
            str[i] = str[len - i - 1];
            str[len - i - 1] = temp;
        }

        // Send reversed string back to client
        MPI_Send(str, strlen(str) + 1, MPI_CHAR, 0, 1, MPI_COMM_WORLD);
    }

    // Finalize MPI environment
    MPI_Finalize();
    return 0;
}

/*
MPI_Init() → start MPI
MPI_Comm_rank() → get process ID (rank)
MPI_Comm_size() → get total number of processes
MPI_Send() → send message to a process
MPI_Recv() → receive message from a process
MPI_Finalize() → end MPI
*/

// Linux
// sudo apt install mpich -y
// mpicc mpi_string_reverse.c -o str-rev
// mpirun -np 2 ./str-rev

```
