package com.jlgproject.zyd.model;

import java.io.Serializable;

/**
 * @author 王锋 on 2017/5/24.
 */

public class DiaoPay implements Serializable{


    /**
     * privateKey : MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCjejputpsoeAdw91EJyPbrjtJG2Avaa9TtuWngL2+9Q1ngZs4zn3gF3gPdwV3XH8jM10l5bt3YWpX4uwJ34bDjYwBGaUqLYIpsZvFZ1iRWAH3X2sWZ5I0unAAd2BsrJAuYzgXcLhpOB3ndzlqx28QoyYEOkjsKrJx+ELQ9hDFiuNMpHKlNtwk11yB6tep1iXpeVQ79E4CYzUUAJzcrYkEOEGidzioq9xgypUb7njGDo3QX25yeGy2ZeOOHY8+zMtfG7kBjMXDccg5IsYNEan/9q0FFznJVGxs+jfukDdlwoJFy7cO4liz8wMjBoYrZKlTJcQ5dC4LTUt1chCOcZ8jZAgMBAAECggEAdMgfL/Hw2zMLlc6HLKdMhTAM1SEHLqxzerNGiCk6+IhBj0XBri0QT5ivSF4XTFNfXb25ti7V+fWURxBJDhzDAV062BHld7WqjQ/pABUy9eD6BRY6rNoRZSd6CdfUaLgiNLGQjB6/GdjLXQFJ1t64va5dLGyYEuMGFCSYrY1evjdH74izzWvZsY30zu0PyTBz4ql1iy6VT4njqr0P0Q9BK2gU091feqzyTuPULrxFqzU6aCzMKnXSL6I++PtC0K5sCxAsuiDOp1uilbfigYXP8tPrpKUSgwAMvXGtdpxH3OEcXEYi15PQKwJtB1jbpubxYBtUKVQX7r030zzCLZyhZQKBgQDSzvgz8dxHVQyH6H6t7oF22ZPu1g/DI+F4L10NtmXIPMtRzXQQ1WGcuw413uayG2WeHqqSTCXv89s9EJ5DolOdU5Nu92Ch9KGwxUz34VZVb6NEjWMiAyJRHW6kp30Rh97xj8UcWEVsSSnVpNfh+ZsHAS6Tfy6IkiS6qxlin80VMwKBgQDGhcNvpNsE6Mg+Iok4of+od/fVMpnPMc9dZOfULQPD39t+TayZigJ9KyrNtZSagSTSRlcA4uJxg8x5ydB9asyOZ7eq4sQJMzTdIZwQ+zzidFT0HbCgY6F3J1v3Ea9Lt4QpbKErirCLVt0kuGTgi5lt4pyw8Uug6u/AmpFu05bRwwKBgGrtZGQkt9DOyO5e9XKP2cdJEYpBtfkLSCBIFfEQpYvtmkvB5K9tLHftQYX9rBKHZwHPGEHgshWGIZxVw5EW00anz86nV7KOfT1GtoW9HKd1WuE4viHQaSWvwiFuezfbLTBl9lssQvpsGfYuCPqsOwBQjylth1Lqngq5IsUtvT6VAoGAJW83ySem/SgACmdsxLcXhGcK3rLp+f4EgjHy3TmXyim+M/TlpRY3He8z5RJmcTQFA3msEki2Nn2J4zVBEUVASWIZainUmX+EamZaDYGym2kgU9/9XGEtI1DSsKYvDfIPA9iuE43VeJDqpm/25bitN+FRdrQpdLCYgRMkdWeEuw0CgYBqEs3+jGh4gIi1ATDj1CftWt2UX1gecaQvK9p5mO9l4DPGuemWEkA8nLq7ynoIMZZw76J1Ot2VI+zjuMFj0yvjcke2fgbT4Ir2OmQKExr5FKybZJFgKDSzD45Hso9s2k/k5aXcXyNSuV6prdhv5Pms00NVO10rZX35Iz405bPr/Q==
     * orderId : 0ced617d94e04dcc96374f9e94dc68d2
     * subject : 备案
     * price : 0.01
     * body : 备案信息
     * notify_url : http://59.110.223.224:9019/alipay/pay/notify
     */

    private DataBean data;
    /**
     * data : {"privateKey":"MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCjejputpsoeAdw91EJyPbrjtJG2Avaa9TtuWngL2+9Q1ngZs4zn3gF3gPdwV3XH8jM10l5bt3YWpX4uwJ34bDjYwBGaUqLYIpsZvFZ1iRWAH3X2sWZ5I0unAAd2BsrJAuYzgXcLhpOB3ndzlqx28QoyYEOkjsKrJx+ELQ9hDFiuNMpHKlNtwk11yB6tep1iXpeVQ79E4CYzUUAJzcrYkEOEGidzioq9xgypUb7njGDo3QX25yeGy2ZeOOHY8+zMtfG7kBjMXDccg5IsYNEan/9q0FFznJVGxs+jfukDdlwoJFy7cO4liz8wMjBoYrZKlTJcQ5dC4LTUt1chCOcZ8jZAgMBAAECggEAdMgfL/Hw2zMLlc6HLKdMhTAM1SEHLqxzerNGiCk6+IhBj0XBri0QT5ivSF4XTFNfXb25ti7V+fWURxBJDhzDAV062BHld7WqjQ/pABUy9eD6BRY6rNoRZSd6CdfUaLgiNLGQjB6/GdjLXQFJ1t64va5dLGyYEuMGFCSYrY1evjdH74izzWvZsY30zu0PyTBz4ql1iy6VT4njqr0P0Q9BK2gU091feqzyTuPULrxFqzU6aCzMKnXSL6I++PtC0K5sCxAsuiDOp1uilbfigYXP8tPrpKUSgwAMvXGtdpxH3OEcXEYi15PQKwJtB1jbpubxYBtUKVQX7r030zzCLZyhZQKBgQDSzvgz8dxHVQyH6H6t7oF22ZPu1g/DI+F4L10NtmXIPMtRzXQQ1WGcuw413uayG2WeHqqSTCXv89s9EJ5DolOdU5Nu92Ch9KGwxUz34VZVb6NEjWMiAyJRHW6kp30Rh97xj8UcWEVsSSnVpNfh+ZsHAS6Tfy6IkiS6qxlin80VMwKBgQDGhcNvpNsE6Mg+Iok4of+od/fVMpnPMc9dZOfULQPD39t+TayZigJ9KyrNtZSagSTSRlcA4uJxg8x5ydB9asyOZ7eq4sQJMzTdIZwQ+zzidFT0HbCgY6F3J1v3Ea9Lt4QpbKErirCLVt0kuGTgi5lt4pyw8Uug6u/AmpFu05bRwwKBgGrtZGQkt9DOyO5e9XKP2cdJEYpBtfkLSCBIFfEQpYvtmkvB5K9tLHftQYX9rBKHZwHPGEHgshWGIZxVw5EW00anz86nV7KOfT1GtoW9HKd1WuE4viHQaSWvwiFuezfbLTBl9lssQvpsGfYuCPqsOwBQjylth1Lqngq5IsUtvT6VAoGAJW83ySem/SgACmdsxLcXhGcK3rLp+f4EgjHy3TmXyim+M/TlpRY3He8z5RJmcTQFA3msEki2Nn2J4zVBEUVASWIZainUmX+EamZaDYGym2kgU9/9XGEtI1DSsKYvDfIPA9iuE43VeJDqpm/25bitN+FRdrQpdLCYgRMkdWeEuw0CgYBqEs3+jGh4gIi1ATDj1CftWt2UX1gecaQvK9p5mO9l4DPGuemWEkA8nLq7ynoIMZZw76J1Ot2VI+zjuMFj0yvjcke2fgbT4Ir2OmQKExr5FKybZJFgKDSzD45Hso9s2k/k5aXcXyNSuV6prdhv5Pms00NVO10rZX35Iz405bPr/Q==","orderId":"0ced617d94e04dcc96374f9e94dc68d2","subject":"备案","price":"0.01","body":"备案信息","notify_url":"http://59.110.223.224:9019/alipay/pay/notify"}
     * state : ok
     * message : 该订单已支付
     */

    private String state;
    private String message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        private String privateKey;
        private String orderId;
        private String subject;
        private String price;
        private String body;
        private String notify_url;

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getNotify_url() {
            return notify_url;
        }

        public void setNotify_url(String notify_url) {
            this.notify_url = notify_url;
        }
    }
}
