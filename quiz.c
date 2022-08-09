#include <stdio.h>
#include <unistd.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <fcntl.h>

int main()
{
    int key,semid,id;
    int tkt1,tkt2,tkt3;
    int fd1,fd2,fd3;

    fd1 = open("tkt1_DB.txt", O_RDWR);
    fd2 = open("tkt2_DB.txt", O_RDWR);
    fd3 = open("tkt3_DB.txt", O_RDWR);

    key = ftok(".", 'x');
    semid = semget(key, 3, 0);

    struct sembuf s[3] = {{0, -1, 0|SEM_UNDO}, {1, -1, 0|SEM_UNDO}, {2, -1, 0|SEM_UNDO}};

    printf("You can choose from these three trains:\n");
    printf("Train no. 1 -> Banglore Express\n");
    printf("Train no. 2 -> kota Express\n");

//   fd1 = open("tkt1_DB.txt", O_RDWR);
//     fd2 = open("tkt2_DB.txt", O_RDWR);
//     fd3 = open("tkt3_DB.txt", O_RDWR);


    printf("Train no. 3 -> Delhi Express\n");
    printf("Please Enter the the train no. you want to book the ticket: ");

    scanf("%d",&id);
    if(id==1)
    {
        printf("Waiting...\n");
        semop(semid, &s[0], 1);
        printf("In critical section of train no. %d\n",id);
        read(fd1, &tkt1, sizeof(int));
        tkt1++;
        lseek(fd1, 0, SEEK_SET);
        write(fd1, &tkt1, sizeof(int));
        printf("Your tkt no. has been allocated successfully\n");
        printf("Ticket no. -> %d\n",tkt1);
        printf("Press Enter to unlock the critical section\n");
        getchar();
        getchar();
        s[0].sem_op = 1;
        semop(semid, &s[0], 1);
    }
    else if(id==2)
    {
        printf("Waiting...\n");
        semop(semid, &s[1], 1);
        printf("In critical section of train no. %d\n",id);
        read(fd2, &tkt2, sizeof(int));
        tkt2++;
        lseek(fd2, 0, SEEK_SET);
        write(fd2, &tkt2, sizeof(int));
        printf("Your tkt no. has been allocated successfully\n");
        printf("Ticket no. -> %d\n",tkt2);
        printf("Press Enter to unlock the critical section\n");
        getchar();
        getchar();
        s[1].sem_op = 1;
        semop(semid, &s[1], 1);        
    }
    else if(id==3)
    {
        printf("Waiting...\n");
        semop(semid, &s[2], 1);
        printf("In critical section of train no. %d\n",id);
        read(fd3, &tkt3, sizeof(int));

        tkt3++;

        lseek(fd3, 0, SEEK_SET);
        write(fd3, &tkt3, sizeof(int));
        printf("Your tkt no. has been allocated successfully\n");
        printf("Ticket no. -> %d\n",tkt3);
        printf("Press Enter to unlock the critical section\n");
        getchar();
        getchar();

        s[2].sem_op = 1;
        semop(semid, &s[2], 1);
    }
    else
    {
        printf("Please Enter the valid train no.\n");
    }
    close(fd1);
    close(fd2);
    close(fd3);
    return 0;
}