<template>
	<div>
		<el-dialog
	      id='qrDialog'
	      append-to-body
	      title="二维码"
	      :visible.sync="dialogVisible"
	      width="50%"
	      show-close
	      close-on-press-escape
	      modal-append-to-body
	      center>
	      <div id='qrzone'></div>
	      <span slot="footer" class="dialog-footer">
	        <el-button @click="dialogVisible = false">另存为</el-button>
	        <el-button type="primary" @click="handleQRCodeClose()">关闭</el-button>
	      </span>
	    </el-dialog>
	</div>
</template>

<script>
    import QRCode from 'qrcodejs2'

	export default {
		props: {  
			dialogVisible: false,
			content: ''
		},
        data(){
        	return {}
        },
        mounted(){
        	this.qrcode(this.content);
        },
        methods: {
        	qrcode (content) { 
                this.dialogVisible = true;
                let qrcode = new QRCode('qrzone', {  
                    width: 100,  
                    height: 100, // 高度  
                    text: content // 二维码内容  
                    // render: 'canvas' // 设置渲染方式（有两种方式 table和canvas，默认是canvas）  
                    // background: '#f0f'  
                    // foreground: '#ff0'  
                });
            },

            handleQRCodeClose() {
                this.dialogVisible = false;
            },
        }
    }
</script>
