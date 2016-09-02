package com.yuncai.core.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import sun.misc.BASE64Decoder;

public class CompressFile {
	// 压缩字符串与解压字符串

	// 这里需要特别注意的是，如果你想把压缩后的byte[]保存到字符串中，
	// 不能直接使用new String(byte)或者byte.toString()，因为这样转换之后容量是增加的。
	// 同样的道理，如果是字符串的话，也不能直接使用new String().getBytes()获取byte[]传入到decompress中进行解压缩。
	// 如果保存压缩后的二进制，可以使用new sun.misc.BASE64Encoder().encodeBuffer(byte[]
	// b)将其转换为字符串。
	// 同样解压缩的时候首先使用new BASE64Decoder().decodeBuffer 方法将字符串转换为字节，然后解压就可以了。

	/**
	 * 压缩字符串为 byte[] 储存可以使用new sun.misc.BASE64Encoder().encodeBuffer(byte[] b)方法
	 * 保存为字符串
	 * 
	 * @param str压缩前的文本
	 * @return
	 */
	public static final byte[] compress(String str) {
		if (str == null)
			return null;

		byte[] compressed;
		ByteArrayOutputStream out = null;

		GZIPOutputStream zout = null;
		try {
			out = new ByteArrayOutputStream();
			zout = new GZIPOutputStream(out);
			zout.write(str.getBytes("utf-8"));
			zout.close();

			compressed = out.toByteArray();
		} catch (IOException e) {
			compressed = null;
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return compressed;
	}

	/**
	 * 将压缩后的 byte[] 数据解压缩
	 * 
	 * @param compressed
	 *            压缩后的 byte[] 数据
	 * @return 解压后的字符串
	 */
	public static final String decompress(byte[] compressed) {
		if (compressed == null)
			return null;

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		GZIPInputStream zin = null;

		String decompressed;
		try {
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new GZIPInputStream(in);
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString("utf-8");
		} catch (IOException e) {
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return decompressed;
	}

	public static void main(String[] args) {
		String str = "这里需要特别注意的是，如果你想把压缩后的byte[]保存到字符串中，"
				+ "不能直接使用new String(byte)或者byte.toString()，因为这样转换之后容量是增加的。"
				+ "同样的道理，如果是字符串的话，也不能直接使用new String().getBytes()获取byte[]传入到decompress中进行解压缩。"
				+ "如果保存压缩后的二进制，可以使用new sun.misc.BASE64Encoder().encodeBuffer(byte[] b)将其转换为字符串。"
				+ "同样解压缩的时候首先使用new BASE64Decoder().decodeBuffer 方法将字符串转换为字节，然后解压就可以了";
		String str2 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><message><head><messageId>yctest201305221408589420</messageId><agentId>yctest</agentId><digest>54583dd18d6ca6507ccc2578c9ae8d68</digest></head><body><update><userName>zill</userName><status>0</status><realName>知龙</realName><mobile>11111111111</mobile><certNo>11111111111111111</certNo><email>test6636@6636.com</email></update></body></message>";
		byte[] compressed = CompressFile.compress(str2);
		//System.out.println(Arrays.toString(compressed));

		String member = Base64.encode(compressed);
		System.out.println(member);
		//System.out.println(" ");
		// 然后解压
		// String
		// ff="H4sIAAAAAAAEAO29B2AcSZYlJi9tynt/SvVK1+B0oQiAYBMk2JBAEOzBiM3mkuwdaUcjKasqgcplVmVdZhZAzO2dvPfee++999577733ujudTif33/8/XGZkAWz2zkrayZ4hgKrIHz9+fB8/Ih7/Hu8WZXqZ101RLT/7eHe883GaL6fVrFhefPbxuj3fPvj49zh6PM+z2dHjRd402UV+Njva29nd3d3ZPdjdv7+zu/dw/9PHd92Xj+nHsqVfpllR5mX++K754PGsuMib9uiAn8d39c/HdwX+pJpdHz1eN3kt/77IFvlRmVXz9e7e47v2E/7u5dXs6Nf7T/70v+jqx//Lv+jv+s//xD/vf/iT/uzxf/VX/OEf/YbSEF/Lb/SD4f4/VHBhX+oAAAA=";
		String g = "H4sIAAAAAAAAAFVQS07DMBS8SpU9jZ0mIZZeXYmyYcGKEzj2cxopsZGdImCNEGLVG7DhaoVr4JJfu7Jm3njezIPNc9ssntD52pp1RJckWqCRVtWmWkf7Tl8V0YZDi96LCjnsUKgJ3ikuRd1ggwmhlBLKKMmytKDXlEA8iyA8ppvVEI8EqLpC3/HwN1VJqpNcEk1XOniUikilZJZKljCIByHEfYLSqhcODv2jNR63ViEPKy/wNL0fwh+/334P7z8fh+Pn16wdp6FUW6K7xS6EDJHLBm9EI4xEzljO8mXwPydBO8TXCabsJLjk/k0epHUYCharwaAnTvc53xf3jcar8T9mI9CYmQEAAA==";
		String f = "H4sIAAAAAAAEAO29B2AcSZYlJi9tynt/SvVK1+B0oQiAYBMk2JBAEOzBiM3mkuwdaUcjKasqgcplVmVdZhZAzO2dvPfee++999577733ujudTif33/8/XGZkAWz2zkrayZ4hgKrIHz9+fB8/Iv6LP+pPjvzvz/yL/vM/8W/8z//yP+o/+/v/9P/qj/mb/6s/6e//z/+Evzf9L/6qv/g//xP+2P/8z/ub/ou//C/4r//iv+O/+KP+tv/q7/+r/4u//I/+z/+yv+f/AbwEjH5GAAAA";
		String d = "H4sIAAAAAAAAAE2QTU4DIRTHr9LM3sIwA6XJK3Xrpneg8BxJYDADY6w3ceXKa3gd4y2kzkdlAfw/En4POL4Gv3nBIbnYH6p6S6sN9iZa13eHasyPd7I6KgiYku5QwRNqu8oHqy4mY8qM1g3ljNUtlVzuW0aB3DpQjj6vZSCLBuu6YijectlYW0srjBac7owxjO+k2WssngQyF4FM75+jvSgYn63OhWlMOJx0QPXmvAeySkhZ5zGpAjPfYEDt/7Lvj8+fr3cgqwEhnp1HVd9WmWHywOCQT/F/tjTmBDBo59V1PCEacX/dtiYGIFNQqGZYMrEv36N+AWJHIE+BAQAA";
		String t = "H4sIAAAAAAAAAFWPsW7CMBCGXwVlp3bsxDHSYYZODDxEYh9pJLArnFZlZ0BMHSqkqgMrIwOIJ8L0MUCCBDGdPt1399/B4Gs66XzizFfO9qP4hUYdtNqZypb96KMed2U0UDBF7/MSFbxhblocGjXXQnDBaMxpynicpJQlieAUyMOBa7F1KwNpGExVoq8V41SyrJCaFjqjedzLxlkqhGQ6SSXLe0DuIpBbfuHMXMEM/buzHl+dQXVNfOK2O7qffv7ZhuXx/7AI69/wtw/rXVh+n1abx1xjArntb15QF2o9mFQlAQAA";
		// byte[] decompressed=Base64.decode(g);
		byte[] des = null;
		try {
			// des=Base64.decode(g);
			//
			// des=new BASE64Decoder().decodeBuffer(g);
			
			des = new BASE64Decoder().decodeBuffer(d);
			//System.out.println(Arrays.toString(des) + "--------");
			String decompresseds = CompressFile.decompress(des);
			System.out.println("-----------------------------------------------");
			System.out.println(decompresseds);
			System.out.println("-----------------------------------------------");
			des = new BASE64Decoder().decodeBuffer(t);
			//System.out.println(Arrays.toString(des) + "--------");
			decompresseds = CompressFile.decompress(des);
			System.out.println(decompresseds);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
