<template>
  <div>
    <el-button @click="getVis=true">发送 GET 请求</el-button>
    <el-button @click="postVis=true">发送 POST 请求</el-button>
    <el-button @click="putVis=true">发送 PUT 请求</el-button>
    <el-button @click="deleteVis=true">发送 DELETE 请求</el-button>
    <el-button @click="patchVis=true">发送 PATCH 请求</el-button>
  </div>

  <el-dialog :model-value="getVis" title="get" :show-close="false" :close-on-click-modal="false">

    <el-form class="inner-form" :model="user">
      <el-form-item label="用户名:">
        <el-input v-model="user.username"></el-input>
      </el-form-item>

    </el-form>

    <span slot="footer" class="dialog-footer">
        <el-button type="danger" size="small" @click="getVis = false">关闭</el-button>
        <el-button type="primary" size="small" @click="sendGetRequest">发送</el-button>
      </span>
  </el-dialog>

  <el-dialog :model-value="postVis" title="post" :show-close="false" :close-on-click-modal="false">

    <el-form class="inner-form" :model="user">
      <el-form-item label="用户名:">
        <el-input v-model="user.username"></el-input>
      </el-form-item>

      <el-form-item label="密码:">
        <el-input v-model="user.password"></el-input>
      </el-form-item>

    </el-form>

    <span slot="footer" class="dialog-footer">
        <el-button type="danger" size="small" @click="postVis = false">关闭</el-button>
        <el-button type="primary" size="small" @click="sendPostRequest">发送</el-button>
      </span>
  </el-dialog>

  <el-dialog :model-value="putVis" title="put" :show-close="false" :close-on-click-modal="false">

    <el-form class="inner-form" :model="user">
      <el-form-item label="用户名:">
        <el-input v-model="user.username"></el-input>
      </el-form-item>

      <el-form-item label="密码:">
        <el-input v-model="user.password"></el-input>
      </el-form-item>

      <el-form-item label="年龄:">
        <el-input v-model="user.age"></el-input>
      </el-form-item>

    </el-form>

    <span slot="footer" class="dialog-footer">
        <el-button type="danger" size="small" @click="putVis = false">关闭</el-button>
        <el-button type="primary" size="small" @click="sendPutRequest">发送</el-button>
      </span>
  </el-dialog>

  <el-dialog :model-value="deleteVis" title="delete" :show-close="false" :close-on-click-modal="false">

    <el-form class="inner-form" :model="user">
      <el-form-item label="用户名:">
        <el-input v-model="user.username"></el-input>
      </el-form-item>

      <el-form-item label="密码:">
        <el-input v-model="user.password"></el-input>
      </el-form-item>

      <el-form-item label="年龄:">
        <el-input v-model="user.age"></el-input>
      </el-form-item>

    </el-form>

    <span slot="footer" class="dialog-footer">
        <el-button type="danger" size="small" @click="deleteVis = false">关闭</el-button>
        <el-button type="primary" size="small" @click="sendDeleteRequest">发送</el-button>
      </span>
  </el-dialog>

  <el-dialog :model-value="patchVis" title="patch" :show-close="false" :close-on-click-modal="false">

    <el-form class="inner-form" :model="user">
      <el-form-item label="用户名:">
        <el-input v-model="user.username"></el-input>
      </el-form-item>

      <el-form-item label="密码:">
        <el-input v-model="user.password"></el-input>
      </el-form-item>

      <el-form-item label="年龄:">
        <el-input v-model="user.age"></el-input>
      </el-form-item>

    </el-form>

    <span slot="footer" class="dialog-footer">
        <el-button type="danger" size="small" @click="patchVis = false">关闭</el-button>
        <el-button type="primary" size="small" @click="sendPatchRequest">发送</el-button>
      </span>
  </el-dialog>

  <el-dialog :model-value="requestVis" title="接收到的信息" :show-close="false" :close-on-click-modal="false">

    <el-card>
      <div slot="header">
        <h3>服务端响应回来的信息</h3>
      </div>
      <div>
        <p><strong>状态码：</strong> {{ request.code }}</p>
        <p><strong>描述信息：</strong> {{ request.msg }}</p>
        <p><strong>响应数据：</strong> {{ request.data }}</p>
      </div>
    </el-card>

    <span slot="footer" class="dialog-footer">
        <el-button type="danger" size="small" @click="requestVis = false">关闭</el-button>
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
      getReq("/user", {
        username: "zs"
      }).then(data => {
        this.$message.success("发送成功")
        this.requestVis = true
        this.request=data.data
      })
    },
    sendPostRequest() {
      postReq('/user/login', {
        username: this.user.username,
        password: this.user.password
      }).then(data => {
        this.$message.success("发送成功")
        this.requestVis = true
        this.request=data.data
      })
    },
    sendPutRequest() {
      putReq('/user', {
        username: this.user.username,
        password: this.user.password,
        age: this.user.age
      }).then(data => {
        this.$message.success("发送成功")
        this.requestVis = true
        this.request=data.data
      })
    },
    sendDeleteRequest() {
      deleteReq('/user').then(data => {
        this.$message.success("发送成功")
        this.requestVis = true
        this.request=data.data
      })
    },
    sendPatchRequest() {
      patchReq('/user').then(data => {
        this.$message.success("发送成功")
        this.requestVis = true
        this.request=data.data
      })
    }
  },
  data() {
    return {
      user: {},
      request: {},
      getVis: false,
      postVis: false,
      putVis: false,
      deleteVis: false,
      patchVis: false,
      requestVis: false
    }
  }
};
</script>