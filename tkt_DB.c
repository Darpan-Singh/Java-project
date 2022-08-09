#include <unistd.h>
#include <stdio.h>
#include <sys/sem.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

union semun{
    int val;
    struct semid_ds *buffer;
    unsigned short int *array;
}arg;

int main()
{
    int key,semid;
    int fd1,fd2,fd3;
    int tkt1=0,tkt2=0,tkt3=0;
    key = ftok(".",'x');
    semid = semget(key, 3, IPC_CREAT|0744);
    static ushort semarray[3]= {1, 1, 1};
    arg.array = semarray;
    semctl(semid, 0, SETALL, arg);
    fd1 = open("tkt1_DB.txt", O_CREAT|O_RDWR, 0744);
    fd2 = open("tkt2_DB.txt", O_CREAT|O_RDWR, 0744);
    fd3 = open("tkt3_DB.txt", O_CREAT|O_RDWR, 0744);
    write(fd1, &tkt1, sizeof(int));
    write(fd2, &tkt2, sizeof(int));
    write(fd3, &tkt3, sizeof(int));
    close(fd1);
    close(fd2);
    close(fd3);
    return 0;
}
