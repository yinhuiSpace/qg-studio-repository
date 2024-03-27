<template>
  <div>
    <el-button @click="getVis=true">发送 GET 请求</el-button>
    <el-button @click="sendPostRequest">发送 POST 请求</el-button>
    <el-button @click="sendPutRequest">发送 PUT 请求</el-button>
    <el-button @click="sendDeleteRequest">发送 DELETE 请求</el-button>
    <el-button @click="sendPatchRequest">发送 PATCH 请求</el-button>
  </div>

  <el-dialog :model-value="getVis" title="get" :show-close="false" :close-on-click-modal="false">

    <el-form class="inner-form" :model="user">
      <el-form-item label="用户名:">
        <el-input v-model="user.username"></el-input>
      </el-form-item>

    </el-form>

    <span slot="footer" class="dialog-footer">
        <el-button type="danger" size="small" @click="getVis = false">关闭</el-button>
        <el-button type="primary" size="small" @click="sendGetRequest">保存</el-button>
      </span>
  </el-dialog>

  <el-dialog :model-value="getVis" title="get" :show-close="false" :close-on-click-modal="false">

    <el-form class="inner-form" :model="user">
      <el-form-item label="用户名:">
        <el-input v-model="user.username"></el-input>
      </el-form-item>

    </el-form>

    <span slot="footer" class="dialog-footer">
        <el-button type="danger" size="small" @click="getVis = false">关闭</el-button>
        <el-button type="primary" size="small" @click="sendGetRequest">保存</el-button>
      </span>
  </el-dialog>

  <el-dialog :model-value="getVis" title="get" :show-close="false" :close-on-click-modal="false">

    <el-form class="inner-form" :model="user">
      <el-form-item label="用户名:">
        <el-input v-model="user.username"></el-input>
      </el-form-item>

    </el-form>

    <span slot="footer" class="dialog-footer">
        <el-button type="danger" size="small" @click="getVis = false">关闭</el-button>
        <el-button type="primary" size="small" @click="sendGetRequest">保存</el-button>
      </span>
  </el-dialog>

  <el-dialog :model-value="getVis" title="get" :show-close="false" :close-on-click-modal="false">

    <el-form class="inner-form" :model="user">
      <el-form-item label="用户名:">
        <el-input v-model="user.username"></el-input>
      </el-form-item>

    </el-form>

    <span slot="footer" class="dialog-footer">
        <el-button type="danger" size="small" @click="getVis = false">关闭</el-button>
        <el-button type="primary" size="small" @click="sendGetRequest">保存</el-button>
      </span>
  </el-dialog>


</template>

<style scoped>

</style>

<script>
import {deleteReq, getReq, patchReq, postReq, putReq} from "@/api/req.js";

export default {
  name: "Http",
  methods: {
    sendGetRequest() {
      getReq("/user",{
        username:"zs"
      }).then(data => {
        this.$message.success("发送成功")
      })
    },
    sendPostRequest() {
      postReq('/user/login',{
        username:this.user.username,
        password:this.user.password
      }).then(data => {
        this.$message.success("发送成功")
      })
    },
    sendPutRequest() {
      putReq('/user',{
        username:this.user.username,
        password:this.user.password,
        age:this.user.age
      }).then(data => {
        this.$message.success("发送成功")
      })
    },
    sendDeleteRequest() {
      deleteReq('/user').then(data => {
        this.$message.success("发送成功")
      })
    },
    sendPatchRequest() {
      patchReq('/user').then(data => {
        this.$message.success("发送成功")
      })
    }
  },
  data(){
    return{
      user:{},
      getVis:false,
      postVis:false,
      putVis:false,
      deleteVis:false,
      patchVis:false,
    }
  }
};
</script>