<template>
  <div v-if="currentAccount" class="edit-form">
    <h4>Accounts</h4>
    <form>
      <div class="form-group">
        <label for="AccountType"> Account Type</label>
        <input
          type="text"
          class="form-control"
          id="AccountType"
          v-model="currentAccount.accountType"
          :disabled="true"
        />
      </div>
      <div class="form-group">
        <label for="AccountType"> Account Balance</label>
        <input
          type="text"
          class="form-control"
          id="AccountBalance"
          v-model="currentAccount.accountBalance"
          :disabled="true"
        />
      </div>
      <div class="form-group" v-if="currentAccount.accountType !== 'Savings'">
        <label for="AccountSavingBalance"
          >Amount from Savings Account Available</label
        >
        <input
          type="text"
          class="form-control"
          id="AccountSavingBalance"
          v-model="currentSavingsAccount.accountBalance"
          :disabled="true"
        />
      </div>
      <div class="form-group">
        <label for="totalMoney">Amount to process</label>
        <input
          type="text"
          class="form-control"
          id="totalMoney"
          v-model="totalMoney"
        />
      </div>
    </form>
    <button
      v-if="
        currentAccount.accountType !== 'Savings' &&
        currentAccount.accountType !== 'Goals' &&
        currentAccount.accountBalance === 0
      "
      class="badge badge-danger mr-2"
      @click="deleteAccount"
    >
      Delete
    </button>
    <button
      v-if="
        currentSavingsAccount.accountBalance > 0 &&
        totalMoney <= currentSavingsAccount.accountBalance
      "
      type="submit"
      class="badge badge-success"
      @click="updateDepositAccount"
    >
      Deposit</button
    ><button
      v-if="totalMoney <= currentAccount.accountBalance"
      type="submit"
      class="badge badge-warning"
      @click="updateWithdrawAccount"
    >
      Withdraw
    </button>

    <p>{{ message }}</p>
  </div>
  <div v-else>
    <br />
    <p>Please click on a Account...</p>
  </div>
</template>
<script>
import AccountDataService from "../services/AccountDataService";
export default {
  name: "accounts-type",
  data() {
    return {
      currentAccount: null,
      currentSavingsAccount: null,
      totalMoney: 0,
      message: "",
      result: 0,
    };
  },
  computed: {},
  methods: {
    getAccount(id) {
      AccountDataService.get(id)
        .then((response) => {
          this.currentAccount = response.data;
          console.log(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getSavingsAccount(id) {
      AccountDataService.get(id)
        .then((response) => {
          this.currentSavingsAccount = response.data;
          console.log(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
    },

    updateDepositAccount() {
      //if transfer is made
      if (this.currentAccount.accountType != "Savings") {
        this.result =
          parseInt(this.totalMoney) +
          parseInt(this.currentAccount.accountBalance);
        this.currentAccount.accountBalance = this.result;
        AccountDataService.update(this.currentAccount.id, this.currentAccount)
          .then((response) => {
            console.log(response.data);
            this.message = "The account was updated successfully!";
          })
          .catch((e) => {
            console.log(e);
          });
        this.result =
          parseInt(this.currentSavingsAccount.accountBalance) -
          parseInt(this.totalMoney);
        this.currentSavingsAccount.accountBalance = this.result;

        AccountDataService.update(
          this.currentSavingsAccount.id,
          this.currentSavingsAccount
        )
          .then((response) => {
            console.log(response.data);
            this.message = "The account was updated successfully!";
          })
          .catch((e) => {
            console.log(e);
          });
      } else {
        this.result =
          parseInt(this.totalMoney) +
          parseInt(this.currentAccount.accountBalance);
        this.currentAccount.accountBalance = this.result;
        AccountDataService.update(this.currentAccount.id, this.currentAccount)
          .then((response) => {
            console.log(response.data);
            this.message = "The account was updated successfully!";
          })
          .catch((e) => {
            console.log(e);
          });
      }
      this.getSavingsAccount(1);
      this.getAccount(this.currentAccount.id);
    },

    updateWithdrawAccount() {
      //if transfer is made
      if (this.currentAccount.accountType != "Savings") {
        this.result =
          parseInt(this.currentAccount.accountBalance) -
          parseInt(this.totalMoney);
        this.currentAccount.accountBalance = this.result;
        AccountDataService.update(this.currentAccount.id, this.currentAccount)
          .then((response) => {
            console.log(response.data);
            this.message = "The account was updated successfully!";
          })
          .catch((e) => {
            console.log(e);
          });
        this.result =
          parseInt(this.currentSavingsAccount.accountBalance) +
          parseInt(this.totalMoney);
        this.currentSavingsAccount.accountBalance = this.result;

        AccountDataService.update(
          this.currentSavingsAccount.id,
          this.currentSavingsAccount
        )
          .then((response) => {
            console.log(response.data);
            this.message = "The account was updated successfully!";
          })
          .catch((e) => {
            console.log(e);
          });
      } else {
        this.result =
          parseInt(this.currentAccount.accountBalance) -
          parseInt(this.totalMoney);
        this.currentAccount.accountBalance = this.result;
        AccountDataService.update(this.currentAccount.id, this.currentAccount)
          .then((response) => {
            console.log(response.data);
            this.message = "The account was updated successfully!";
          })
          .catch((e) => {
            console.log(e);
          });
      }
      this.getSavingsAccount(1);
      this.getAccount(this.currentAccount.id);
    },
    deleteAccount() {
      AccountDataService.delete(this.currentAccount.id)
        .then((response) => {
          console.log(response.data);
          this.$router.push({ name: "accounts" });
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },

  mounted() {
    this.message = "";
    this.getAccount(this.$route.params.id);
    this.getSavingsAccount(1);
  },
};
</script>
<style>
.edit-form {
  max-width: 300px;
  margin: auto;
}
</style>