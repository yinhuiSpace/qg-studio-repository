#include <iostream>
#include <algorithm>
#include <string.h>
#include <math.h>
#include <stdlib.h>
#include <stdio.h>
#include <windows.h>
#define ll long long
#include "SingleLinkedList.h"
using namespace std;

//实现单链表ADT、实现双向链表ADT、周记一篇
//1.单链表奇偶调换(例:input:1->2->3->4,output:2->1->4->3)
//2.找到单链表的中点
//3.判断链表是否成环
//4.反转链表(递归和非递归)
//要求:按照项目工程结构开发，要有良好的交互设计、用户输入处理、规范的代码风格。
//周记要使用Markdown语法，按照规定格式书写。
//多写注释，让师兄师姐更方便的读懂你们的代码！
//截止时间:3月17号(周日)晚上12点前上交至导师处
//(作业与周记需上传至github，提交时仅需发送链接)
//周记分为以下四个部分
//1.生活随记
//2.一周总结
//3.存在问题
//4.下周规划

//声明全局变量
//头节点指针
SingleNode* head;
//当前节点指针
SingleNode* present;

//初始化链表
void initSingleLinkedList(){

    //为头节点分配空间
    head=(SingleNode*) malloc(sizeof (SingleNode));

    //当前节点指针指向头节点
    present=head;

}

//插入新节点
void insertNode(int data){

    //为新节点分配空间
    SingleNode* newNode=(SingleNode*) malloc(sizeof (SingleNode));

    //存入数据
    newNode->data=data;
    newNode->next=NULL;

    //插入尾部
    present->next=newNode;

    //移动当前指针
    present=newNode;
}

//查找储存指定数据的节点，约定返回第一个符合条件的节点的指针
SingleNode* selectNode(int data){

    //创建一个用于遍历的指针
    SingleNode* p=head;

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

//删除储存指定数据的节点
void deleteNode(int data){

    //遍历链表
    SingleNode* p=selectNode(data);

    //删除节点
    p->next=p->next->next;

    //释放节点空间
    free(p->next);
}

//打印链表
void printLinkedList(){

    //遍历链表
    //创建一个用于遍历的指针
    SingleNode* p=head;

    //遍历链表
    while (p!=NULL){
        //当遍历到符合条件的节点时就返回该节点的父节点的指针
        p=p->next;
        if (p!=NULL){
            //打印
            printf("%d->",p->data);
        }
    }
}

//销毁链表
void destroySingleLinkedList(){

    //遍历节点，逐个释放
    //创建一个用于遍历的指针
    SingleNode* p=head;

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

