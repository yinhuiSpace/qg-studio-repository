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

//分别定义前缀表达式和后缀表达式字符数组
char infix[100];
char postfix[100];

//定义栈结构
typedef struct {
    int data[100];
    int top;
} Stack;

//初始化栈
void initStack(Stack* stack) {
    stack->top = -1;
}

//判断栈是否为空
int isEmpty(Stack* stack) {
    return (stack->top == -1);
}

//压栈
void push(Stack* stack, int value) {
    stack->data[++stack->top] = value;
}

//弹栈
int pop(Stack* stack) {
    if (isEmpty(stack)) {
        return 0;
    }
    return stack->data[stack->top--];
}

//将栈顶元素弹出
int peek(Stack* stack) {
    if (isEmpty(stack)) {
        return 0;
    }
    return stack->data[stack->top];
}

//判断字符是否为运算符
int isOperator(char c) {
    return (c == '+' || c == '-' || c == '*' || c == '/');
}

//判断运算符优先级
int precedence(char c) {
    if (c == '+' || c == '-') {
        return 1;
    } else if (c == '*' || c == '/') {
        return 2;
    }
    return 0;
}

//根据不同的运算符进行运算
int applyOperation(int a, int b, char opt) {
    switch (opt) {
            case '+':
            return a + b;
            case '-':
            return a - b;
            case '*':
            return a * b;
            case '/':
            return a / b;
            default:
            return 0;
    }
}

//前缀表达式转后缀表达式
void infixToPostfix(char* infix, char* postfix) {
    //初始化栈
    Stack stackS1;
    initStack(&stackS1);

    int length = strlen(infix);
    int j = 0; // 后缀表达式字符串的索引

    //核心代码
    for (int i = 0; i < length; i++) {
        if (infix[i] == ' ' || infix[i] == '\t') {
            continue;
        } else if (isdigit(infix[i])) {
            //处理操作数
            while (i < length && (isdigit(infix[i]) || infix[i] == '.')) {
                //操作数直接放入后缀表达式
                postfix[j++] = infix[i++];
            }
            postfix[j++] = ' ';
            i--;
        } else if (isalpha(infix[i])) {
            //处理字符
            while (i < length && (isalpha(infix[i]) || isdigit(infix[i]))) {
                postfix[j++] = infix[i++];
            }
            postfix[j++] = ' ';
            i--;
        } else if (infix[i] == '(') {
            //左括号入栈
            push(&stackS1, infix[i]);
        } else if (infix[i] == ')') {
            //右括号
            while (!isEmpty(&stackS1) && peek(&stackS1) != '(') {
                postfix[j++] = pop(&stackS1);
                postfix[j++] = ' ';
            }
            pop(&stackS1); // 弹出左括号
        } else if (isOperator(infix[i])) {
            //对于运算符需要比较运算优先级
            while (!isEmpty(&stackS1) && precedence(peek(&stackS1)) >= precedence(infix[i])) {
                postfix[j++] = pop(&stackS1);
                postfix[j++] = ' ';
            }
            //运算符入栈
            push(&stackS1, infix[i]);
        }
    }

    while (!isEmpty(&stackS1)) {
        postfix[j++] = pop(&stackS1);
        postfix[j++] = ' ';
    }
    postfix[j] = '\0'; // 添加字符串结束符
}

int evaluatePostfix(char* postfix) {
    Stack stack;
    initStack(&stack);

    int length = strlen(postfix);
    for (int i = 0; i < length; i++) {
        if (isdigit(postfix[i])) {
            int operand = 0;
            while (i < length && isdigit(postfix[i])) {
                operand = operand * 10 + (postfix[i] - '0');
                i++;
            }
            i--;
            push(&stack, operand);
        } else if (isOperator(postfix[i])) {
            int operand2 = pop(&stack);
            int operand1 = pop(&stack);
            //根据栈，计算结果
            int result = applyOperation(operand1, operand2, postfix[i]);
            push(&stack, result);
        }
    }

    return pop(&stack);
}

void show(void) {
    printf("\n\t链栈计算器\n");
    printf("1.开始计算\n");
    printf("2.转成后缀表达式\n");
    printf("3.退出\n");
    printf("\n请输入对应的数字(1-3)：");
    scanf("%d", &cmd);
}

//根据用户输入，执行不同的函数
void executeCmd() {
    switch (cmd) {
        //根据用户输入的算式进行计算
        case 1:{
            printf("请输入要计算的算式：");
            scanf("%s",infix);
            infixToPostfix(infix,postfix);
            int result = evaluatePostfix(postfix);
            printf("计算结果为: %d\n", result);
            break;
        }
        //转为后缀表达式
        case 2:{
            printf("请输入要转换的中缀表达式：");
            scanf("%s",infix);
            infixToPostfix(infix,postfix);
            puts(postfix);
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