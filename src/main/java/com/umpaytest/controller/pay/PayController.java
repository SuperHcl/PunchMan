package com.umpaytest.controller.pay;

import com.github.joekerouac.common.tools.io.InMemoryFile;
import com.mxbc.pay.common.vo.Result;
import com.mxbc.pay.common.vo.SoaFile;
import com.mxbc.pay.common.vo.SoaInputStream;
import com.mxbc.pay.common.vo.Trans;
import com.mxbc.pay.sdk.BillClient;
import com.mxbc.pay.sdk.SoaFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@RestController
@RequestMapping("/pay")
public class PayController {


    @GetMapping("/test")
    public void test() throws Exception {
        BillClient billClient = ClientManagerHolder.clientManager.getBillClient(ClientManagerHolder.bizCode);

        List<Trans> list = new ArrayList<>();
        Future<Result<List<SoaFile>>> resultFuture = billClient.getSoaAllTradeFiles("20241107");
        Result<List<SoaFile>> listResult = resultFuture.get();
        List<SoaFile> data = listResult.getData();
        data.forEach(e -> {
            Future<Result<InMemoryFile>> fileFuture = billClient.getSoaFile(e);
            try {
                Result<InMemoryFile> fileResult = fileFuture.get();
                if (fileResult.isSuccess()) {
                    // 解析文件，获取支付流水resultFuture = {ClientManagerImpl$RequestClientImpl$1@11089}
                    SoaInputStream stream = SoaFileUtil.convert(fileResult.getData());
                    Trans read = stream.read();
                    while (read != null) {
                        log.info("支付流水：{}", read);
                        list.add(read);
                        read = stream.read();
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        System.out.println("支付流水总数：" + list.size());
    }


}
