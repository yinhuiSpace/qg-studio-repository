


//结构体：单链表节点
typedef struct SingleNode{

    //指针域
    struct SingleNode* next;

    //数据域,暂且规定数据类型是整型
    int data;
}SingleNode;

extern SingleNode* head;

extern SingleNode* present;




//声明函数

//初始化链表,为头指针分配空间，需要维护一个当前节点指针，便于随时定位到尾部
void initSingleLinkedList();

//销毁链表
void destroySingleLinkedList();

//插入新节点，在单链表尾部插入新节点，传入链表尾节点指针
void insertNode(int data);

//删除储存指定数据的节点
void deleteNode(int data);

//查找储存指定数据的节点，约定返回第一个符合条件的节点的指针
SingleNode* selectNode(int data);

//打印链表
void printLinkedList();
