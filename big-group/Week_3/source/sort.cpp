#include <iostream>
#include <algorithm>
#include <string.h>
#include <math.h>
#include <stdlib.h>
#include <stdio.h>
#include <windows.h>
#include <time.h>

#define ll long long

using namespace std;

//命令编号
int cmd;

//数组长度
int len;

//待排序的数组
int nums[100];

void getData();

void putResult();

// 插入排序
void insertionSort(int arr[], int n) {
    int i, key, j;
    for (i = 1; i < n; i++) {
        key = arr[i];
        j = i - 1;
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j = j - 1;
        }
        arr[j + 1] = key;
    }
}

// 归并排序
void merge(int arr[], int l, int m, int r) {
    int i, j, k;
    int n1 = m - l + 1;
    int n2 = r - m;

    int L[n1], R[n2];

    for (i = 0; i < n1; i++)
        L[i] = arr[l + i];
    for (j = 0; j < n2; j++)
        R[j] = arr[m + 1 + j];

    i = 0;
    j = 0;
    k = l;
    while (i < n1 && j < n2) {
        if (L[i] <= R[j]) {
            arr[k] = L[i];
            i++;
        } else {
            arr[k] = R[j];
            j++;
        }
        k++;
    }

    while (i < n1) {
        arr[k] = L[i];
        i++;
        k++;
    }

    while (j < n2) {
        arr[k] = R[j];
        j++;
        k++;
    }
}

void mergeSort(int arr[], int l, int r) {
    if (l < r) {
        int m = l + (r - l) / 2;

        mergeSort(arr, l, m);
        mergeSort(arr, m + 1, r);

        merge(arr, l, m, r);
    }
}

// 快速排序
int partition(int arr[], int low, int high) {
    int pivot = arr[high];
    int i = (low - 1);

    for (int j = low; j <= high - 1; j++) {
        if (arr[j] < pivot) {
            i++;
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
    int temp = arr[i + 1];
    arr[i + 1] = arr[high];
    arr[high] = temp;
    return (i + 1);
}

void quickSort(int arr[], int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}

// 计数排序
void countSort(int arr[], int n) {
    int output[n + 1];
    int max = arr[0];
    for (int i = 1; i < n; i++) {
        if (arr[i] > max)
            max = arr[i];
    }
    int count[max + 1];

    for (int i = 0; i <= max; ++i)
        count[i] = 0;

    for (int i = 0; i < n; i++)
        count[arr[i]]++;

    for (int i = 1; i <= max; i++)
        count[i] += count[i - 1];

    for (int i = n - 1; i >= 0; i--) {
        output[count[arr[i]] - 1] = arr[i];
        count[arr[i]]--;
    }

    for (int i = 0; i < n; i++)
        arr[i] = output[i];
}

// 基数计数排序
void radixCountSort(int arr[], int n, int exp) {
    int output[n];
    int i, count[10] = {0};

    for (i = 0; i < n; i++)
        count[(arr[i] / exp) % 10]++;

    for (i = 1; i < 10; i++)
        count[i] += count[i - 1];

    for (i = n - 1; i >= 0; i--) {
        output[count[(arr[i] / exp) % 10] - 1] = arr[i];
        count[(arr[i] / exp) % 10]--;
    }

    for (i = 0; i < n; i++)
        arr[i] = output[i];
}

void radixSort(int arr[], int n) {
    int max = arr[0];
    for (int i = 1; i < n; i++) {
        if (arr[i] > max)
            max = arr[i];
    }

    for (int exp = 1; max / exp > 0; exp *= 10)
        radixCountSort(arr, n, exp);
}

// 生成随机数
void generateRandomArray(int arr[], int n) {
    for (int i = 0; i < n; i++)
        arr[i] = rand() % 10000; // 生成 0 到 9999 之间的随机数
}

// 打印数组
void printArray(int arr[], int n) {
    for (int i = 0; i < n; i++)
        printf("%d ", arr[i]);
    printf("\n");
}


void show(void) {
    printf("\n\t数组排序\n");
    printf("1.插入排序\n");
    printf("2.归并排序\n");
    printf("3.快速排序\n");
    printf("4.计数排序\n");
    printf("5.基数排序\n");
    printf("6.测试\n");
    printf("7.退出\n");
    printf("\n请输入对应的数字(1-7)：");
    scanf("%d", &cmd);
}

void getData() {
    printf("数组长度:");
    scanf("%d",&len);
    printf("\n");
    printf("请逐个输入数据:");
    for (int i = 0; i < len; ++i) {
        scanf("%d",&nums[i]);
    }
}

void testRadixSort(clock_t start, clock_t end, double cpu_time_used, int n, int *arr) {// 生成新的随机数组
    generateRandomArray(arr, n);

    // 测试基数排序
    start = clock();
    radixSort(arr, n);
    end = clock();
    cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
    printf("基数排序用时: %f 秒\n", cpu_time_used);
}

void testCountSort(clock_t start, clock_t end, double cpu_time_used, int n, int *arr) {// 生成新的随机数组
    generateRandomArray(arr, n);

    // 测试计数排序
    start = clock();
    countSort(arr, n);
    end = clock();
    cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
    printf("计数排序用时: %f 秒\n", cpu_time_used);
}

void testQuickSort(clock_t start, clock_t end, double cpu_time_used, int n, int *arr) {// 生成新的随机数组
    generateRandomArray(arr, n);

    // 测试快速排序
    start = clock();
    quickSort(arr, 0, n - 1);
    end = clock();
    cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
    printf("快速排序用时: %f 秒\n", cpu_time_used);
}

void testMergeSort(clock_t start, clock_t end, double cpu_time_used, int n, int *arr) {// 生成新的随机数组
    generateRandomArray(arr, n);

    // 测试归并排序
    start = clock();
    mergeSort(arr, 0, n - 1);
    end = clock();
    cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
    printf("归并排序用时: %f 秒\n", cpu_time_used);
}

void testInsertSort(clock_t start, clock_t end, double cpu_time_used, int n, int *arr) {
    generateRandomArray(arr, n);

    // 测试插入排序
    start = clock();
    insertionSort(arr, n);
    end = clock();
    cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
    printf("插入排序用时: %f 秒\n", cpu_time_used);
}

//测试排序算法
void testSort() {
    clock_t start, end;
    double cpu_time_used;

    int n = 10000; // 测试数据量

    // 生成随机数组
    int arr[n];

    //分别测试各种排序方式
    testInsertSort(start, end, cpu_time_used, n, arr);
    testMergeSort(start, end, cpu_time_used, n, arr);
    testQuickSort(start, end, cpu_time_used, n, arr);
    testCountSort(start, end, cpu_time_used, n, arr);
    testRadixSort(start, end, cpu_time_used, n, arr);
}

//根据用户输入，执行不同的函数
void executeCmd() {
    switch (cmd) {
        //插入排序
        case 1:{
            //接收用户输入数据
            getData();

            insertionSort(nums,len);

            //打印结果
            putResult();

            break;
        }
            //归并排序
        case 2:{
            //接收用户输入数据
            getData();

            mergeSort(nums,0,len-1);

            //打印结果
            putResult();

            break;
        }
            //快速排序
        case 3:{
            //接收用户输入数据
            getData();

            quickSort(nums,0,len-1);

            //打印结果
            putResult();

            break;
        }
            //计数排序
        case 4:{
            //接收用户输入数据
            getData();

            countSort(nums,len);

            //打印结果
            putResult();

            break;
        }
            //基数排序
        case 5:{
            //接收用户输入数据
            getData();

            radixSort(nums,len);

            //打印结果
            putResult();

            break;
        }
            //测试
        case 6:{
            testSort();
            break;
        }
        default:
            printf("%s\n", "[错误]:无效命令");

    }
}

void putResult() {
    printf("排序结果：");
    for (int i = 0; i < len; ++i) {
        printf("%d ",nums[i]);
    }
}

int main() {

    while (1){

        show();

        if(cmd == 7){
            break;
        }

        executeCmd();
    }

    testSort();


    return 0;
}

