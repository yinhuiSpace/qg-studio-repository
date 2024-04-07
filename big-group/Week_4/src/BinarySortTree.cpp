#include <iostream>
#include <algorithm>
#include <string.h>
#include <math.h>
#include <stdlib.h>
#include <stdio.h>
#include <windows.h>
#include <time.h>

#define ll long long

#include <iostream>
#include <stack>
#include <queue>

using namespace std;

//命令编号
int cmd;

// 定义二叉排序树节点
struct Node {
    int value;
    Node *left;
    Node *right;
};

// 二叉排序树
class BinarySortTree {
public:
    BinarySortTree() : root(nullptr) {}

    // 插入节点
    void insert(int value);

    // 删除节点
    void remove(int value);

    // 层序遍历
    void levelOrder();

    bool search(int key);

    void inOrderRecursive();

    void inOrderIterative();

    void preOrderRecursive();

    void preOrderIterative();

    void postOrderRecursive();

    void postOrderIterative();

    Node *root;

    // 创建节点
    Node *createNode(int value);

    // 插入节点
    void insertNode(Node *root, int value);

    // 删除节点
    Node *removeNode(Node *root, int value);

    // 查找最小节点
    Node *findMin(Node *root);

    bool search(Node *root, int key);

    void inOrderRecursive(Node *root);

    void preOrderRecursive(Node *root);

    void postOrderRecursive(Node *root);

};

BinarySortTree bst;

// 插入节点
void BinarySortTree::insert(int value) {
    if (root == nullptr) {
        root = createNode(value);
    } else {
        insertNode(root, value);
    }
}

// 删除节点
void BinarySortTree::remove(int value) {
    root = removeNode(root, value);
}

// 层序遍历
void BinarySortTree::levelOrder() {
    if (root == nullptr) return;

    queue<Node *> q;
    q.push(root);

    while (!q.empty()) {
        Node *node = q.front();
        q.pop();
        cout << node->value << " ";
        if (node->left != nullptr) q.push(node->left);
        if (node->right != nullptr) q.push(node->right);
    }
    cout << endl;
}

// 创建节点
Node *BinarySortTree::createNode(int value) {
    Node *newNode = new Node;
    newNode->value = value;
    newNode->left = nullptr;
    newNode->right = nullptr;
    return newNode;
}

// 插入节点
void BinarySortTree::insertNode(Node *root, int value) {
    if (value < root->value) {
        if (root->left == nullptr) {
            root->left = createNode(value);
        } else {
            insertNode(root->left, value);
        }
    } else if (value > root->value) {
        if (root->right == nullptr) {
            root->right = createNode(value);
        } else {
            insertNode(root->right, value);
        }
    }
}

// 删除节点
Node *BinarySortTree::removeNode(Node *root, int value) {
    if (root == nullptr) return root;

    if (value < root->value) {
        root->left = removeNode(root->left, value);
    } else if (value > root->value) {
        root->right = removeNode(root->right, value);
    } else {
        if (root->left == nullptr) {
            Node *temp = root->right;
            delete root;
            return temp;
        } else if (root->right == nullptr) {
            Node *temp = root->left;
            delete root;
            return temp;
        }

        Node *temp = findMin(root->right);
        root->value = temp->value;
        root->right = removeNode(root->right, temp->value);
    }
    return root;
}

// 查找最小节点
Node *BinarySortTree::findMin(Node *root) {
    while (root->left != nullptr) {
        root = root->left;
    }
    return root;
}

// 查找数值
bool BinarySortTree::search(int key) {
    return search(root, key);
}

bool BinarySortTree::search(Node* root, int key) {
    if (root == nullptr) {
        return false;
    } else if (root->value == key) {
        return true;
    } else if (root->value > key) {
        return search(root->left, key);
    } else {
        return search(root->right, key);
    }
}

// 中序遍历（递归）
void BinarySortTree::inOrderRecursive() {
    inOrderRecursive(root);
}

void BinarySortTree::inOrderRecursive(Node* root) {
    if (root != nullptr) {
        inOrderRecursive(root->left);
        cout << root->value << " ";
        inOrderRecursive(root->right);
    }
}

// 中序遍历（非递归）
void BinarySortTree::inOrderIterative() {
    stack<Node*> s;
    Node* curr = root;
    while (curr != nullptr || !s.empty()) {
        while (curr != nullptr) {
            s.push(curr);
            curr = curr->left;
        }
        curr = s.top();
        s.pop();
        cout << curr->value << " ";
        curr = curr->right;
    }
}

// 前序遍历（递归）
void BinarySortTree::preOrderRecursive() {
    preOrderRecursive(root);
}

void BinarySortTree::preOrderRecursive(Node* root) {
    if (root != nullptr) {
        cout << root->value << " ";
        preOrderRecursive(root->left);
        preOrderRecursive(root->right);
    }
}

// 前序遍历（非递归）
void BinarySortTree::preOrderIterative() {
    if (root == nullptr) return;
    stack<Node*> s;
    s.push(root);
    while (!s.empty()) {
        Node* curr = s.top();
        s.pop();
        cout << curr->value << " ";
        if (curr->right != nullptr) s.push(curr->right);
        if (curr->left != nullptr) s.push(curr->left);
    }
}

// 后序遍历（递归）
void BinarySortTree::postOrderRecursive() {
    postOrderRecursive(root);
}

void BinarySortTree::postOrderRecursive(Node* root) {
    if (root != nullptr) {
        postOrderRecursive(root->left);
        postOrderRecursive(root->right);
        cout << root->value << " ";
    }
}

// 后序遍历（非递归）
void BinarySortTree::postOrderIterative() {
    if (root == nullptr) return;
    stack<Node*> s1, s2;
    s1.push(root);
    while (!s1.empty()) {
        Node* curr = s1.top();
        s1.pop();
        s2.push(curr);
        if (curr->left != nullptr) s1.push(curr->left);
        if (curr->right != nullptr) s1.push(curr->right);
    }
    while (!s2.empty()) {
        cout << s2.top()->value << " ";
        s2.pop();
    }
}


void show(void) {
    printf("\n\t数组排序\n");
    printf("1.插入数值\n");
    printf("2.删除数值\n");
    printf("3.查找数值\n");
    printf("4.中序遍历（递归）\n");
    printf("5.中序遍历（非递归）\n");
    printf("6.前序遍历（递归）\n");
    printf("7.前序遍历（非递归）\n");
    printf("8.前序遍历（递归）\n");
    printf("9.前序遍历（非递归）\n");
    printf("10.层序遍历\n");
    printf("11.退出\n");
    printf("\n请输入对应的数字(1-11)：");
    scanf("%d", &cmd);
}

//根据用户输入，执行不同的函数
void executeCmd() {


    switch (cmd) {
        case 1: {
            printf("请输入一个数值：");
            int value;
            cin>>value;
            bst.insert(value);
            printf("插入成功");
            break;
        }
        case 2: {
            printf("请输入要删除的数值：");
            int value;
            scanf("%d", &value);
            bst.remove(value);
            printf("插入成功");
            break;
        }
        case 3: {
            printf("请输入要查找的数值：");
            int value;
            scanf("%d", &value);
            if (bst.search(value)) {
                cout << "数值已找到。" << endl;
            } else {
                cout << "数值未找到。" << endl;
            }
            break;
        }
        case 4:
            cout << "中序遍历（递归）: ";
            bst.inOrderRecursive();
            cout << endl;
            break;
        case 5:
            cout << "中序遍历（非递归）: ";
            bst.inOrderIterative();
            cout << endl;
            break;
        case 6:
            cout << "前序遍历（递归）: ";
            bst.preOrderRecursive();
            cout << endl;
            break;
        case 7:
            cout << "前序遍历（非递归）: ";
            bst.preOrderIterative();
            cout << endl;
            break;
        case 8:
            cout << "后序遍历（递归）: ";
            bst.postOrderRecursive();
            cout << endl;
            break;
        case 9:
            cout << "后序遍历（非递归）: ";
            bst.postOrderIterative();
            cout << endl;
            break;
        case 10:
            cout << "层序遍历: ";
            bst.levelOrder();
            cout << endl;
            break;
        default:
            printf("%s\n", "[错误]:无效命令");

    }
}


int main() {

    while (1) {

        show();

        if (cmd == 11) {
            break;
        }

        executeCmd();
    }

    return 0;
}

