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
#include <sys/socket.h>
#include <sys/types.h>
#include <arpa/inet.h>
#include <unistd.h>

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

    int sock_fd = socket(AF_INET, SOCK_STREAM, 0);
    if(sock_fd < 0){
        printf("create sockfd error\n");
        exit(1);
    }
    socklen_t len = sizeof(server);
    printf("connect...\n");
    int ret = connect(sock_fd, (struct sockaddr*)&server, len);
    if(ret == -1){
        printf("connect error\n");
        exit(1);
    }else{
        printf("connect success\n");
    }
    char buffer[1024];
    //while(1){
        bzero(buffer, 1024);
        //scanf("%s", buffer);
        //mark1 检测帐号是否存在 yes
        strcpy(buffer, "{\"mark\":1,\"account\":\"1111\"}\r\n");
        //new mark2 
        //flag = 1 帐号注册
        //flag = 2 验证密码修改密宝
        //flag = 3 验证密宝修改密码
        //strcpy(buffer, "{\"mark\":2, \"account\":\"zhuchenguan\", \"secret\":\"123\", \"flag\":1, \"question\":\"you phone\", \"answer\":\"5029054897\"}\r\n");
        //mark3 obtain question yes
        //strcpy(buffer, "{\"mark\":3,\"account\":\"zhuz\"}\r\n");
        //mark4 修改密码 yes
        //strcpy(buffer, "{\"mark\":4,\"account\":\"1111\", \"secret\":\"weihao\"}\r\n");
        //mark5 修改资料 yes
        //strcpy(buffer, "{\"mark\":5,\"account\":\"zhuchen\",\"name\":\"hahahahaha\", \"head\":\"1\"}\r\n");
        //mark6 更新联系
        //注意返回为空也算失败
        //update数据，并且更新isUpdate数值
        //strcpy(buffer, "{\"mark\":6,\"account\":\"zhuchen\", info:{\"name\":\"acdacd\", \"head\":\"1\"}, update:[{\"type\":4, \"content\":\"1111111111111\"}]}\r\n");
        //mark7 本地没有数据，获得所有的数据 yes
        //strcpy(buffer, "{\"mark\":7,\"account\":\"zhu\"}\r\n");
        //mark8 本地有数据，获得需要更新的数据 yes
        //strcpy(buffer, "{\"mark\":8,\"account\":\"zhu\"}\r\n");
        //mark9 yes 获取好友信息
        //strcpy(buffer, "{\"mark\":9, \"account\":\"\", \"friendaccount\":\"2\"}\r\n");
        //mark10 借用mark11生成的二维码的信息组成新的json请求
        //strcpy(buffer, "{\"mark\":10, \"account\":\"1111\", \"friendaccount\":\"5555\", \"qid\":8}\r\n");
        //mark11 yes 新建二维码
        //strcpy(buffer, "{\"mark\":11, \"account\":\"2222\", \"authority\":\"20\", \"time_out\":\"30\"}\r\n");
        //mark12 yes 登录
        //strcpy(buffer, "{\"mark\":12,\"account\":\"2222\", \"secret\":\"2222\"}\r\n");
        //mark13 yes 删除好友   漏洞:好友不存在也能删除
        //strcpy(buffer, "{\"mark\":13,\"account\":\"003308\", \"friendaccount\":\"1111\"}\r\n");
        //mark14 一次性录入数据 需要改进，若用户没有填写某一信息，则不往数据库写记录
        //strcpy(buffer, "{\"mark\":14, \"account\":\"8888\", \"personInfo\":{\"name\":\"123123\", \"head\":\"1\"},\"phoneInfo\":{\"personPhoneNumber\":\"123123\", \"homePhoneNumber\":\"123123\", \"workPhoneNumber\":\"123123\"},emailInfo:{\"personMailNumber\":\"123123\", \"homeMailNumber\":\"123123\", \"workMailNumber\":\"123123\"},\"qqNumber\":\"123123\", \"weiboNumber\":\"123123\"}\r\n");
        //mark15 添加好友
        //strcpy(buffer, "{\"mark\":15, \"account\":\"zhu\", \"friendaccount\":\"zhuchen\"}\r\n");
        //mark 16 设置 add friend question
        //strcpy(buffer, "{\"mark\":16, \"account\":\"zhu\", \"addquestion\":\"who\", \"addanswer\":\"e\"}\r\n");
        //mark 17 设置 add friend question
        //strcpy(buffer, "{\"mark\":17, \"account\":\"zhu\", \"friendaccount\":\"zhuchen\", \"answer\":\"e\"}\r\n");
        //mark 18 obtain self info
        //strcpy(buffer, "{\"mark\":18, \"account\":\"zhu\"}\r\n");
        //mark 19 backup data
        //strcpy(buffer, "{\"mark\":19, \"account\":\"zhu\", contacts:[{\"name\":\"wang\", \"phone\":\"18829292929\"}, {\"name\":\"wei\", \"phone\":\"17728282828\"}]}\r\n");
        //mark 20 obtain backup data
        //strcpy(buffer, "{\"mark\":20, \"account\":\"zhuchenguan\"}\r\n");
        //mark 21 add friend info
        //strcpy(buffer, "{\"mark\":21, \"account\":\"zhu\", \"friendaccount\":\"zuchen\"}\r\n");
        //mark 22 check update friend is success
        //strcpy(buffer, "{\"mark\":22, \"account\":\"zhu\"}\r\n");
        send(sock_fd, buffer, 1024, 0);
        char buf[10000];
        recv(sock_fd, buf, 10000, 0);
        printf("%s\n", buf);
    //}



	return EXIT_SUCCESS;
}



