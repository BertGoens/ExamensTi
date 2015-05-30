#include <stdio.h>
#include <stdlib.h>

#define poolSize (1024*1024)
int pool[poolSize];
int* poolPtr = pool;

int main(int argc, char* argv)
{
    int aantal_bytes = 0;
    char* heapPtr[10];

    for(int i = 0; 1; i++)
    {
        if(i == 10) {
            i = 0;
        }
        printf("Geef een aantal te alloceren bytes op de heap \n");
        scanf("%d", aantal_bytes);
        heapPtr[i] = (char*) malloc(aantal_bytes * sizeof(char));
    }

    return(0);
}