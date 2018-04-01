package com.mJunction.drm.service;

import com.mJunction.drm.common.exception.FileDeleteException;
import com.mJunction.drm.common.exception.FileReadException;
import com.mJunction.drm.common.exception.ReinitiateBidderException;
import com.mJunction.drm.pojo.BidderPojo;

import java.io.FileNotFoundException;

/**
 * Created by siddhartha.kumar on 4/11/2017.
 */
public interface BidderService {

    BidderPojo populateBidderSync(BidderPojo bidderPojo);
    BidderPojo reinitiateBidder(BidderPojo bidderPojo) throws FileNotFoundException, FileReadException, ReinitiateBidderException, FileDeleteException;
    BidderPojo viewXmlBidder(BidderPojo bidderPojo) throws FileNotFoundException, FileReadException;
}
