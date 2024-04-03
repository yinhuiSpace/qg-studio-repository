<template>
  <div>
    <!-- 表格 -->
    <el-table :data="tableData" style="width: 100%">

      <el-table-column prop="userId" label="索引"></el-table-column>
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="gender" label="性别">
        <template #default="dataObj">
          <span v-if="dataObj.row['gender']===1">男</span>
          <span v-if="dataObj.row['gender']===2">女</span>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
      <el-table-column prop="email" label="邮件"></el-table-column>
      <el-table-column prop="userStatus" label="状态">
        <template #default="dataObj">
          <span v-if="dataObj.row['userStatus']===1">可用</span>
          <span v-if="dataObj.row['userStatus']===0">禁用</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import $ from 'jquery'
import axios from "axios";
export default {
  name:'User',
  methods: {
    // //分页查询
    queryByPage: function () {
      axios({
        method: 'GET',
        url: 'http://localhost:12500/user',
      }).then((resp) => {
        console.log(resp);
        this.tableData=resp.data.data
      })
    },



  },
  mounted() {
    this.queryByPage();
  },
  data() {
    return {
      //时间范围查找
      form: {
        range: [],
        title: '',
        pageNum: this.currentPage,
        pageSize: this.pageSize
      },
      //表单数据模型
      formData: {
        username: '',
        password: '',
        confirmPassword: '',
        phone: '',
        email: '',
      },
      //表格数据,从数据库获得
      tableData: [],
      currentPage: 1,
      pageSize: 5,
      total: 3,
      dialogVisible: false,
      //表格一行数据
      rowData: {},
      //多选id
      multipleSelection: [],
      updateDialog: false,
      pwd:'',
      indexKey:0,
      insertVis:false
    };
  },
};
</script>



