package com.nagakawa.guarantee.service;

import com.nagakawa.guarantee.service.impl.SmsServiceImpl;

public interface SmsService {

	SmsServiceImpl.SmsResponse send(String sendTo, String message);
}
