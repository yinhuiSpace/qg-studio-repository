#include "DoubleLinkedList.h"
#include <iostream>
#include <algorithm>
#include <string.h>
#include <math.h>
#include <stdlib.h>
#include <stdio.h>
#include <windows.h>
#define ll long long
using namespace std;

//声明全局变量
//头节点指针
DoubleNode* head;
//尾节点指针
DoubleNode* tail;

//初始化链表
void initDoubleLinkedList(){

    //为头节点分配空间
    head=(DoubleNode*) malloc(sizeof (DoubleNode));
    head->prev=NULL;
    head->next=tail;

    //当前节点指针指向头节点
    tail=(DoubleNode*) malloc(sizeof (DoubleNode));
    tail->prev=head;
    tail->next=NULL;

}

//插入新节点
void insertNode(int data){

    //为新节点分配空间
    DoubleNode* newNode=(DoubleNode*) malloc(sizeof (DoubleNode));

    tail->prev->next=newNode;

    //存入数据
    newNode->data=data;
    newNode->next=NULL;
    newNode->prev=tail;

    //插入尾部
    tail->prev=newNode;
    tail->next=NULL;
}

//查找储存指定数据的节点，约定返回第一个符合条件的节点的指针
DoubleNode* selectNode(int data){

    //遍历链表
    //创建一个用于遍历的指针
    DoubleNode* p=head;

    //遍历链表
    while (p!=NULL){
        //当遍历到符合条件的节点时就返回该节点的父节点的指针
        if(p->next->data==data){
            return p;
        }
        p=p->next;
    }

    //没有找到对应节点，返回NULL
    return NULL;
}


//打印链表
void printLinkedList(){

    //遍历链表
    //创建一个用于遍历的指针
    DoubleNode* p=head;

    //遍历链表
    while (p!=NULL){
        //当遍历到符合条件的节点时就返回该节点的父节点的指针
        p=p->next;
        if (p!=NULL){
            //打印
            printf("<-%d->",p->data);
        }
    }
}

//销毁链表
void destroyDoubleLinkedList(){

    //遍历节点，逐个释放
    //创建一个用于遍历的指针
    DoubleNode* p=head;

    //遍历链表
    while (p!=NULL){
        //当遍历到符合条件的节点时就返回该节点的父节点的指针
        p=p->next;
        if (p!=NULL){
            //释放
            free(p);
        }
    }
    //释放头节点
    free(head);
}


