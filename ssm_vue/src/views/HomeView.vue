<template>
  <div>
    <div style="margin: 10px 5px">
      <el-button type="primary" @click="add">新增</el-button>
      <el-button>其他</el-button>
    </div>
    <div style="margin: 10px 5px">
      <el-input v-model="search" style="width: 30%" placeholder="请输入关键字"/>
      <el-button style="margin-left: 10px" type="primary" @click="list">检索</el-button>
    </div>
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="id" label="ID" sortable></el-table-column>
      <el-table-column prop="name" label="家居名"></el-table-column>
      <el-table-column prop="maker" label="厂家"></el-table-column>
      <el-table-column prop="price" label="价格"></el-table-column>
      <el-table-column prop="sales" label="销量"></el-table-column>
      <el-table-column prop="stock" label="库存"></el-table-column>
      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button type="text" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确认删除吗？" @confirm="handleDel(scope.row.id)">
            <template #reference>
              <el-button size="small" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="提示" v-model="dialogVisible" width="30%">
      <el-form :model="form" :rules="rules" ref="form" label-width="120px">
        <el-form-item label="家居名" prop="name">
          <el-input v-model="form.name" style="width: 50%"></el-input>
          {{ serverValidErrors.name }}
        </el-form-item>
        <el-form-item label="厂商" prop="maker">
          <el-input v-model="form.maker" style="width: 50%"></el-input>
          {{ serverValidErrors.maker }}
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" style="width: 50%"></el-input>
          {{ serverValidErrors.price }}
        </el-form-item>
        <el-form-item label="销量" prop="sales">
          <el-input v-model="form.sales" style="width: 50%"></el-input>
          {{ serverValidErrors.sales }}
        </el-form-item>
        <el-form-item label="库存" prop="stock">
          <el-input v-model="form.stock" style="width: 50%"></el-input>
          {{ serverValidErrors.stock }}
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="save">确 定</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </span>
      </template>
    </el-dialog>

    <div style="margin: 10px 0">
      <el-pagination
          @size-change="handlePageSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[2,5,10]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: 'HomeView',
  components: {},
  data() {
    return {
      serverValidErrors: {},
      currentPage: 1,
      pageSize: 5,
      total: 10,
      search: '',
      form: {},
      dialogVisible: false,
      tableData: [],
      rules: {
        name: [
          {required: true, message: '请输入称家居名', trigger: 'blur'}
        ],
        maker: [
          {required: true, message: '请输入称制造商', trigger: 'blur'}
        ],
        price: [
          {required: true, message: '请输入价格', trigger: 'blur'},
          {pattern: /^(([1-9]\d*)|(0))(\.\d+)?$/, message: '请输入数字', trigger: 'blur'}
        ],
        sales: [
          {required: true, message: '请输入销量', trigger: 'blur'},
          {pattern: /^(([1-9]\d*)|(0))$/, message: '请输入数字', trigger: 'blur'}
        ],
        stock: [
          {required: true, message: '请输入库存', trigger: 'blur'},
          {pattern: /^(([1-9]\d*)|(0))$/, message: '请输入数字', trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    this.list();
  },
  methods: {
    add() {
      this.dialogVisible = true;
      this.form = {};
      this.$nextTick(() => {
        this.$refs['form'].resetFields()
      });
      this.serverValidErrors = {};
    },
    save() {
      if (this.form.id) {
        request.put("/api/update", this.form).then(res => {
          if (res.code === 200) {
            this.$message(
                {
                  type: "success",
                  message: "修改成功"
                }
            )
          } else {
            this.$message(
                {
                  type: "error",
                  message: "修改失败"
                }
            )
          }
          this.dialogVisible = false;
          this.list();
        })
      } else {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            request.post("/api/save", this.form).then(res => {
              if (res.code === 200) {
                // console.log("res-", res);
                this.dialogVisible = false;
                this.list();
              } else if (res.code === 400) {
                this.serverValidErrors.name = res.extend.errorMsg.name;
                this.serverValidErrors.maker = res.extend.errorMsg.maker;
                this.serverValidErrors.sales = res.extend.errorMsg.sales;
                this.serverValidErrors.price = res.extend.errorMsg.price;
                this.serverValidErrors.stock = res.extend.errorMsg.stock;
              }
            })
          } else {
            this.$message(
                {
                  type: "error",
                  message: "验证失败,不提交"
                }
            )
          }
          return false;
        })
      }
    },
    list() {
      // request.get("/api/furns").then(res => {
      //   console.log("res-", res);
      //   this.tableData = res.extend.furnList;
      // })
      request.get("/api/furnsByConditionPage", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        this.tableData = res.extend.pageInfo.list
        this.total = res.extend.pageInfo.total
      })
    },
    handleEdit(row) {
      // console.log("row--",row)
      // this.form = JSON.parse(JSON.stringify(row));
      request.get("/api/find/" + row.id).then(res => {
        this.form = res.extend.furn;
      })
      this.dialogVisible = true;
    },
    handleDel(id) {
      // console.log(id);
      request.delete("/api/del/" + id).then(res => {
        if (res.code === 200) {
          this.$message(
              {
                type: "success",
                message: res.msg
              }
          )
        } else {
          this.$message(
              {
                type: "error",
                message: res.msg
              }
          )
        }
        this.list()
      })
    },
    handleCurrentChange(pageNum) {
      // console.log(pageNum);
      this.currentPage = pageNum;
      this.list();
    },
    handlePageSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.list();
    }
  }
}
</script>

<style>

</style>