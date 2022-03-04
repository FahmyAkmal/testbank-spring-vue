<template>
  <div class="submit-form">
    <div v-if="!submitted">
      <div class="form-group">
        <label for="accountType">Account Type</label>
        <input
          type="text"
          class="form-control"
          id="accountType"
          required
          v-model="account.accountType"
          name="accountType"
          :disabled="true"
        />
      </div>
      <div class="form-group">
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
        <label for="accountDeposit"> Amount to Deposit </label>
        <input
          class="form-control"
          id="accountDeposit"
          required
          v-model="account.accountBalance"
          name="accountDeposit"
        />
      </div>
      <button @click="saveAccount" class="btn btn-success">Submit</button>
    </div>
    <div v-else>
      <h4>You submitted successfully!</h4>
      <button class="btn btn-success" @click="newAccount">
        Add Investment Account
      </button>
    </div>
  </div>
</template>

<script>
import AccountDataService from "../services/AccountDataService";

export default {
  name: "add-accounts",
  data() {
    return {
      account: {
        id: null,
        accountType: "Investment",
        accountBalance: "",
      },
      currentSavingsAccount: [],
      submitted: false,
    };
  },
  computed: {},
  methods: {
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
    saveAccount() {
      this.result =
        parseInt(this.currentSavingsAccount.accountBalance) -
        parseInt(this.account.accountBalance);
      this.currentSavingsAccount.accountBalance = this.result;
      var data = {
        accountType: this.account.accountType,
        accountBalance: this.account.accountBalance,
      };
      AccountDataService.create(data)
        .then((response) => {
          this.account.id = response.data.id;
          console.log(response.data);
          this.submitted = true;
        })
        .catch((e) => {
          console.log(e);
        });
      AccountDataService.update(
        this.currentSavingsAccount.id,
        this.currentSavingsAccount
      )
        .then((response) => {
          console.log(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    newAccount() {
      this.submitted = false;
      this.account = { accountType: "Investment" };
      this.getSavingsAccount(1);
    },
  },
  mounted() {
    this.getSavingsAccount(1);
  },
};
</script>




<style>
.submit-form {
  max-width: 300px;
  margin: auto;
}
</style>
