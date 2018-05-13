package servelt;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.google.gson.Gson;

import model.BaseBean;
import model.UserBean;
import util.DBUtils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/ChangeMoneyServlet2")
public class ChangeMoneyServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * 支付宝支付业务：入参app_id
	 */
	public static final String APPID = "2016091000480898";
	public static final String URL = "https://openapi.alipaydev.com/gateway.do";// 支付宝网关
	// 支付宝公钥，由支付宝生成
	public static final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";
	/** 商户私钥，pkcs8格式 */
	/** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
	/** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
	/** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
	/** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
	/**
	 * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097
	 * &docType=1
	 */
	public static final String RSA2_PRIVATE = "";
	// 开发者应用私钥，由开发者自己生成
	public static final String RSA_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDZj64uDXwEsmNySYNDp+lLybb1BS0uN5NCdJTo2z8XArVcoCYVS9in2mWOKislLWn3DRKc0THeIvBZE32/swuOeoafaudHOX8fByrgV3wJpQdzFhHPQEE7F+GNDh/X84syA6IWd9iaemT48ydnIFSJs3F4dqZckHYlvDZW97fToFbSPLVaYJ0eTdGxliYjXOyrR0kA7MEDVYHcEyeVsk+KJ7Qhs4oF9L6azRWixDDINy8u2SdzebnC8TFzj9dgFqgPnVhiL1NG/GHiA4JBGEGdc4lB/MtcKlMF4TPuBfaMgDyebelXM4QY9fYwUsFHtsnKnaVb5V04cYTXfaNlDGWzAgMBAAECggEAMBlE3+eZSIyL0eOQwq9ns5q3+TmYwIQG6YVOuqBmNVci84RwhvrKvmlZVHHcMg71Q2o/eC4DwWGmE3tsrKoP3wMVqj7/PM7oXNq/zvNod9VlbTsu2swQ39iqw/2gaqTsXDVTa3Y5jAWNN0DCsFkEd6EVcBWDOCm0BSm0Ncv0dzOuA9rIb8VxxEOt4egnscYKNmOqkUNONYEEiTtnGr8BvF5Dg0BdcBAjpuluViVcK5+3MNH7vIFlRITXs+Pzh0DsNButFeEAY0v628nDbUIJCdyp7K1oA0Zzxn8AnXVtyQ9LroL239du9c4o2eTKG6Isgl6JljTL5KsR8G1G6RMK+QKBgQD89Ia3Fc0QE8HjPeTSs2XvlwO6tGIzK7rPVQeYtPemsS1zNNC2RsdH/R+DRViPR2wYtKT8nHwoCbue0USk8brB/nIweHqp/ZSyJ4rpUJI44u2X0EGX4w/Gh4F9P/KlENcedFGXwvQyT8w09yr8geQGPxTkdAsHaHP9kJAXLAGlBwKBgQDcLha9wAdgal+Cky1rXKPbshTt4yH7rGaOg7l7TGZEE7m/bFlskolc1im5dI3QYMwrOnzhoVisI7anzj3ZjfXIrwWSSR1OuuJAJ8sSDqhyfpi/DCYVcS4SWgiWf1LZpWj3waSAUN8in8f7ondmoImhjwymddkz4W4Mgcu5YKNa9QKBgQCIZCzTQV0NgymzJqdf+L1I/4tSO3sLjlOvAXEUJNe3uKcCDINRFT7UkSZRuK10rBUcCSNA1fuX9w+EJeA2c+S4P0NA4WV6jTGFEg8zmG8PX0Su6+rCQ/s4l835Q+bInBjx4dQw2TykeCRqlq9F8Z+Kwq64M93Sg76vBSi8Zc0JNwKBgADAp5M+dMf/lRP9LMqRJn45vZiSjisuC6uxB5FEUZUp/BiLZkLYvV9z2/CmVVXA/vGm4YZj5smv1Y/9RHjZ410sO/ikB1WdjehqOmd1ZV3+0MbWY8ru+BlX9W+OP9o+ln1CTC2kGR8lLKnPhFj1c4L52jE3deaXfqjMSMX5bpWBAoGBAJYE/ATuByb795DyUY5bTPYnfbwuyTiBNVgX3hGTFw8NWdfaZjSSq7bG7iNBYJUa+amCZCiZUbhae+t6kGahSuKWcbC23EEYCsfmGxZwpriQLNojR1NTA3YRwlSE+r24iMnMOU9FPtseT8UPnBJ3Pk1xRoDmRKZRulPJHuW0LSpV";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangeMoneyServlet2() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		BaseBean data = new BaseBean(); // 基类对象，回传给客户端的json对象

		AlipayClient alipayClient = new DefaultAlipayClient(URL, APPID, RSA_PRIVATE, "json", "UTF-8", ALIPAY_PUBLIC_KEY,
				"RSA");
		AlipayFundTransToaccountTransferRequest req = new AlipayFundTransToaccountTransferRequest();
		req.setBizContent("{" + "    \"out_biz_no\":\"3142321423432\"," + "    \"payee_type\":\"ALIPAY_LOGONID\","
				+ "    \"payee_account\":\"exkxea4366@sandbox.com\"," + "    \"amount\":\"12.23\","
				+ "    \"payer_show_name\":\"上海交通卡退款\"," + "    \"payee_real_name\":\"\"," + "    \"remark\":\"转账备注\","
				+ "  }");
		AlipayFundTransToaccountTransferResponse res;
		try {
			res = alipayClient.execute(req);
			if (res.isSuccess()) {
				System.out.println("调用成功");
				data.setCode(1);
				data.setMsg("调用成功");
			} else {
				System.out.println("调用失败");
				data.setCode(0);
				data.setMsg("调用失败");
			}
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new Gson();
		String json = gson.toJson(data); // 将对象转化成json字符串
		try {
			response.setHeader("Content-Type", "text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().println(json); // 将json数据传给客户端
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close(); // 关闭这个流，不然会发生错误的
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
