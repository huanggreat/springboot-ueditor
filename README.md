# springboot-ueditor
springboot ueditor 整合案例，


## Ueditor 为了解决上传单个图片跨域问题，ueditor.all.js 做过调整

domUtils.on(input, 'change', function(){
                if(!input.value) return;
                var loadingId = 'loading_' + (+new Date()).toString(36);
                var params = utils.serializeParam(me.queryCommandValue('serverparam')) || '';
 
                var imageActionUrl = me.getActionUrl(me.getOpt('imageActionName'));
                var allowFiles = me.getOpt('imageAllowFiles');
 
                me.focus();
                me.execCommand('inserthtml', '<img class="loadingclass" id="' + loadingId + '" src="' + me.options.themePath + me.options.theme +'/images/spacer.gif" title="' + (me.getLang('simpleupload.loading') || '') + '" >');
 
                function callback(responseData){
                    try{
 
                        if(responseData.state == 'SUCCESS' && responseData.url) {
                            loader = me.document.getElementById(loadingId);
                            loader.setAttribute('src', responseData.url);
                            loader.setAttribute('_src', responseData.url);
                            loader.setAttribute('title', responseData.title || '');
                            loader.setAttribute('alt', responseData.original || '');
                            loader.removeAttribute('id');
                            domUtils.removeClasses(loader, 'loadingclass');
                        } else {
                            showErrorLoader && showErrorLoader(responseData.state);
                        }
                    }catch(er){
                        showErrorLoader && showErrorLoader(me.getLang('simpleupload.loadError'));
                    }
                    form.reset();
                    domUtils.un(iframe, 'load', callback);
                }
                function showErrorLoader(title){
                    if(loadingId) {
                        var loader = me.document.getElementById(loadingId);
                        loader && domUtils.remove(loader);
                        me.fireEvent('showmessage', {
                            'id': loadingId,
                            'content': title,
                            'type': 'error',
                            'timeout': 4000
                        });
                    }
                }
 
                /* 判断后端配置是否没有加载成功 */
                if (!me.getOpt('imageActionName')) {
                    errorHandler(me.getLang('autoupload.errorLoadConfig'));
                    return;
                }
                // 判断文件格式是否错误
                var filename = input.value,
                fileext = filename ? filename.substr(filename.lastIndexOf('.')):'';
                if (!fileext || (allowFiles && (allowFiles.join('') + '.').indexOf(fileext.toLowerCase() + '.') == -1)) {
                    showErrorLoader(me.getLang('simpleupload.exceedTypeError'));
                    return;
                }
 
                var fileForm = new FormData();
                fileForm.append(me.options.imageFieldName,input.files[0]);
 
                var oReq = new XMLHttpRequest();
                oReq.open("POST",imageActionUrl,true);
                oReq.send(fileForm);   // 使用XMLHttpRequest的send()把数据发送出去
 
                oReq.onreadystatechange = function(responseText) {//服务器返回值的处理函数，此处使用匿名函数进行实现 
                    if (oReq.readyState == 4 && oReq.status == 200) {// 
                        var responseData = eval("("+oReq.responseText+")");
                        callback(responseData);
                    } 
                }; 
            });

## 本示例中url的路径采用的是外部路径，可根据自己的服务器部署要求灵活配置

### 如有任何疑问，请联系我QQ ：8150772

