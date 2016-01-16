/*
 * =====================================================================================
 *
 *       Filename:  client.c
 *
 *    Description:  
 *
 *
 *        Version:  1.0
 *        Created:  2015年06月25日 00时05分00秒
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:   (wangweihao), 578867817@qq.com
 *        Company:  xiyoulinuxgroup
 *
 * =====================================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <string.h>
#include <strings.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <thread>
#include <memory>
#include <vector>
#include <iostream>

#define SOCKETMAX 100
char buffer[200];
char recvbuffer[SOCKETMAX][200];

void Func(int sock_fd, int i){
    std::cout << "func" << std::endl;
    send(sock_fd, buffer, 1024, 0);
    recv(sock_fd, recvbuffer[i], 200, 0);
    printf("recv...\n");
    printf("%s\n", recvbuffer[i]);
    std::cout << "==============================" << i << std::endl;
    sleep(50);
}


int main(int argc, char *argv[])
{
    if(argc < 2){
        printf("argument error\n");
        exit(1);
    }
	struct sockaddr_in server;
    bzero(&server, sizeof(server));
    server.sin_port = htons((int)atoi(argv[2]));
    server.sin_family = AF_INET;
    inet_pton(AF_INET, argv[1], &server.sin_addr);

    int sock_fd[SOCKETMAX];
    bzero(buffer, 200);
    strcpy(buffer, "{\"mark\":1,\"account\":\"1111\"}");
    std::vector<std::shared_ptr<std::thread>> vst(SOCKETMAX);
    std::cout << vst.size() << std::endl;
    for(int i = 0; i < SOCKETMAX; ++i){
        sock_fd[i] = socket(AF_INET, SOCK_STREAM, 0);
        if(sock_fd[i] < 0){
            printf("create sockfd error\n");
            exit(1);
        }
        socklen_t len = sizeof(server);
        printf("connect...\n");
        int ret = connect(sock_fd[i], (struct sockaddr*)&server, len);
        if(ret == -1){
            printf("connect error\n");
            exit(1);
        }
    }
    //for(int i = 0; i < SOCKETMAX; i++){
    //    std::cout << i << std::endl;
    //    std::cout << "create thread " << std::endl;
    //    auto sv = std::make_shared<std::thread>(Func, sock_fd[i], i);
    //    sv->detach();
    //    std::cout << "--------------------+++" << std::endl;
    //}
    //std::cout << "hava fun" << std::endl;
    sleep(100);
    //char buffer[1024];
    ////while(1){
    //    bzero(buffer, 1024);
    //    //scanf("%s", buffer);
    //    //mark1 检测帐号是否存在 yes
    //    strcpy(buffer, "{\"mark\":1,\"account\":\"1111\"}");
    //    //mark2 帐号注册 yes
    //    //strcpy(buffer, "{\"mark\":2,\"account\":\"tryaccountregister\", \"secret\":\"123123123\"}");
    //    //mark3 验证密宝 yes
    //    //strcpy(buffer, "{\"mark\":3,\"account\":\"1111\", \"type\":\"1\", \"verify\":\"1888292929\"}");
    //    //mark4 修改密码 yes
    //    //strcpy(buffer, "{\"mark\":4,\"account\":\"1111\", \"secret\":\"weihao\"}");
    //    //mark5 修改资料 yes
    //    //strcpy(buffer, "{\"mark\":5,\"account\":\"1111\",\"name\":\"hahahahaha\", \"head\":\"*********\"}");
    //    //mark6 需要增加标记，看是更新联系还是插入联系
    //    //2是Update 1是Insert yes
    //    //注意返回为空也算失败
    //    //update数据，并且更新isUpdate数值
    //    //strcpy(buffer, "{\"mark\":6,\"account\":\"3333\", \"type\":256, \"isUpdateOrInsert\":1, \"contact\":\"18829292929\"}");
    //    //mark7 本地没有数据，获得所有的数据 yes
    //    //strcpy(buffer, "{\"mark\":7,\"account\":\"2222\"}");
    //    //mark8 本地有数据，获得需要更新的数据 yes
    //    //strcpy(buffer, "{\"mark\":8,\"account\":\"4444\"}");
    //    //mark9 yes
    //    //strcpy(buffer, "{\"mark\":9, \"account\":\"4444\", \"friendaccount\":\"5555\"}");
    //    //mark10 借用mark11生成的二维码的信息组成新的json请求
    //    //strcpy(buffer, "{\"mark\":10, \"account\":\"1111\", \"friendaccount\":\"5555\", \"qid\":8}");
    //    //mark11 yes 新建二维码
    //    //strcpy(buffer, "{\"mark\":11, \"account\":\"2222\", \"authority\":\"20\", \"time_out\":\"30\"}");
    //    //mark12 yes 登录
    //    //strcpy(buffer, "{\"mark\":12,\"account\":\"2222\", \"secret\":\"2222\"}");
    //    //mark13 yes 删除好友
         //strcpy(buffer, "{\"mark\":13,\"account\":\"5555\", \"friendaccount\":\"3333333\"}");
    //    //mark14 一次性录入数据 需要改进，若用户没有填写某一信息，则不网数据库写记录
    //    //strcpy(buffer, "{\"mark\":14, \"account\":\"5555\", \"personInfo\":{\"name\":\"123123\", \"head\":\"123123\"},\"phoneInfo\":{\"personPhoneNumber\":\"123123\", \"homePhoneNumber\":\"123123\", \"workPhoneNumber\":\"123123\"},emailInfo:{\"personMailNumber\":\"123123\", \"homeMailNumber\":\"123123\", \"workMailNumber\":\"123123\"},\"qqNumber\":\"123123\", \"weiboNumber\":\"123123\"}");
    //    send(sock_fd, buffer, 1024, 0);
    //    char buf[10000];
    //    recv(sock_fd, buf, 10000, 0);
    //    printf("%s\n", buf);
    ////}



	return EXIT_SUCCESS;
}



