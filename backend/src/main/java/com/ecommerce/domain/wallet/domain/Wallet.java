package com.ecommerce.domain.wallet.domain;

import com.ecommerce.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;
import java.math.BigInteger;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "wallets")
@Entity
public class Wallet {

    public static final String WALLET_DIRECTORY = System.getProperty("user.home")+ File.separator+"wallet"+File.separator;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private Long id;

    @Column(name = "wallet_address")
    private String address;

    @Column(name = "wallet_file_name")
    private String fileName;

    @Column(name = "wallet_balance")
    private BigInteger balance;

    @Column(name = "wallet_cash")
    private int walletCash;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Wallet(final String address, final String fileName, final BigInteger balance, final int walletCash, final User user) {
        this.address = address;
        this.fileName = fileName;
        this.balance = balance;
        this.walletCash = walletCash;
        this.user = user;
        user.changeWallet(this);
    }

    public Wallet changeBalance(final BigInteger balance) {
        this.balance = balance;
        return this;
    }

    public Wallet addBalance(final BigInteger amount) {
        final BigInteger result = balance.add(amount);
        this.balance = result;
        return this;
    }

    public Wallet subtractBalance(final BigInteger amount) {
        final BigInteger result = balance.subtract(amount);
        if(result.longValue() < 0) {
            throw new IllegalArgumentException();
        }
        this.balance = result;
        return this;
    }

}
