#include <iostream>
#include <algorithm>
#include <string.h>
#include <math.h>
#include <stdlib.h>
#include <stdio.h>
#include <windows.h>

#define ll long long

using namespace std;

//命令编号
int cmd;

//链栈节点
typedef struct Node {
    int data;
    struct Node *next;
} Node;

//链栈结构体
typedef struct {
    Node *top;
} Stack;

Node *top;

// 初始化链栈
void initStack() {
    top = NULL;
}

// 压入栈顶元素
void push(int data) {
    //为新节点分配空间
    Node *newNode = (Node *) malloc(sizeof(Node));
    newNode->data = data;
    //压栈：栈指针永远指向栈顶元素
    newNode->next = top;
    //栈指针执行新节点，随着栈指针的不断移动，
    top = newNode;
}

// 弹出栈顶元素
int pop() {
    //栈指针到栈底
    if (top == NULL) {
        printf("栈中已经没有元素\n");
        return -1;
    }
    int value = top->data;
    Node *temp = top;
    top = top->next;
    free(temp);
    return value;
}


void show(void) {
    printf("\n\t链栈\n");
    printf("1.初始化链栈\n");
    printf("2.压入栈顶元素\n");
    printf("3.弹出栈顶元素\n");
    printf("4.退出\n");
    printf("\n请输入对应的数字(1-4)：");
    scanf("%d", &cmd);
}

//根据用户输入，执行不同的函数
void executeCmd() {
    int data;

    switch (cmd) {
        //初始化链栈
        case 1:
            initStack();
            break;
            //压入栈顶元素
        case 2:
            printf("请输入要入栈的数据（只支持十进制整数）：");
            scanf("%d", &data);
            push(data);
            break;
            //弹出栈顶元素
        case 3: {
            int head = pop();
            if (head != -1) {
                printf("栈顶元素为：\n");
                printf("%d\n", head);
            }
            break;
        }
        default:
            printf("%s\n", "[错误]:无效命令");

    }
}


int main() {

    while (1) {
        show();
        if (cmd == 4) {
            break;
        }
        executeCmd();
    }

    return 0;
}