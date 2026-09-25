#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <omp.h>

int main(int argc, char *argv[]) {

    int num_threads = 4;

    if (argc > 1) {
        num_threads = atoi(argv[1]);
    }

    double start = omp_get_wtime();

    #pragma omp parallel num_threads(num_threads)
    {
        int tid = omp_get_thread_num();
        double result = 0.0;

        for (long i = 0; i < 10000000; i++) {
            result += sqrt((double)i);
        }

        printf("Thread %d completed. Result = %.2f\n", tid, result);
    }

    double end = omp_get_wtime();

    printf("\nThreads: %d\n", num_threads);
    printf("Execution time: %.6f seconds\n", end - start);

    return 0;
}
