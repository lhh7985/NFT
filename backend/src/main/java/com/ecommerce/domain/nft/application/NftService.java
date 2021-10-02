package com.ecommerce.domain.nft.application;

import com.ecommerce.domain.item.domain.Item;
import com.ecommerce.domain.item.domain.ItemRepository;
import com.ecommerce.domain.nft.domain.NftRequest;
import com.ecommerce.domain.user.domain.User;
import com.ecommerce.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.businesslogin.BusinessLogin;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

import static org.reflections.Reflections.log;

@Service
@RequiredArgsConstructor
public class NftService {
    private final static Web3j web3j = Web3j.build(new HttpService());
    @Value("${eth.erc721.contract}")
    private String NFT_CONTRACT_ADDRESS;

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BusinessLogin businessLogin;


    //    private BusinessLogin businessLogin = credentialsUtils.getInstance();

    public Item createNftToken(NftRequest nftRequest) throws Exception {
        log.info("nft test start");
        TransactionReceipt r = businessLogin.mint(nftRequest.getCID(),nftRequest.getWalletAddress()).send();
        BigInteger tokenId = businessLogin.getCount().send();
        int count = Integer.parseInt(String.valueOf(tokenId)) - 1;
        Tuple3<String, BigInteger, String> c = businessLogin.charactors(BigInteger.valueOf(count)).send();
        log.info("nft test end");

        User user = userRepository.findById(nftRequest.getUserId()).orElseThrow(IllegalArgumentException::new);

        Item item = Item.builder()
                .name(nftRequest.getName())
                .description(nftRequest.getDescription())
                .price(nftRequest.getPrice())
                .cid(nftRequest.getCID())
                .token_id(c.component2())
                .user(user)
                .build();
        itemRepository.save(item);
        return item;
    }

    public boolean approveAddress(int tokenId, String toAddress) throws Exception {
        businessLogin.approve(toAddress, BigInteger.valueOf(tokenId)).send();
        return true;
    }

    public boolean transferNft(String fromAddress, String toAddress, int tokenId) throws Exception {
        approveAddress(tokenId,toAddress);
        businessLogin.transferFrom(fromAddress,toAddress,BigInteger.valueOf(tokenId)).send();
        User user = userRepository.findByAddress(toAddress).orElseThrow(IllegalArgumentException::new);
        Item item = itemRepository.findByTokenId(BigInteger.valueOf(tokenId)).orElseThrow(IllegalArgumentException::new);
        item.updateUser(user);
        itemRepository.save(item);
        return true;
    }

    public String deployCredential() throws Exception {
        Credentials credentials = WalletUtils.loadCredentials("eth0second", "C:\\Users\\multicampus\\BCSSAFY\\0928\\backend\\src\\main\\resources\\key\\test.wallet");
        ContractGasProvider contractGasProvider = new ContractGasProvider() {
            @Override
            public BigInteger getGasPrice(String contractFunc) {
                return BigInteger.valueOf(100_000_0L);
            }

            @Override
            public BigInteger getGasPrice() {
                return BigInteger.valueOf(100_000_0L);
            }

            @Override
            public BigInteger getGasLimit(String contractFunc) {
                return BigInteger.valueOf(8000000);
            }

            @Override
            public BigInteger getGasLimit() {
                return BigInteger.valueOf(8000000);
            }
        };
        BusinessLogin businessLogin = BusinessLogin.deploy(web3j, credentials, contractGasProvider).send();
        return businessLogin.getContractAddress();
    }


}