package _05_ShoppingCart.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;

import _04_listItems.model.OrderItemBean;
import _04_listItems.model.item_DAO;

public class ShoppingCart {
	private Map<Integer, OrderItemBean> cart = new LinkedHashMap<>();

	public ShoppingCart() {
	}

	public Map<Integer, OrderItemBean> getContent() { // ${ShoppingCart.content}
		return cart;
	}

	public boolean addToCart(int itemID, OrderItemBean oi) {

		item_DAO idao = null;
		try {
			idao = new item_DAO();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		if (oi.getQty() <= 0) {
			return false;
		}
		// 如果客戶在伺服器端沒有此項商品的資料，則客戶第一次購買此項商品
		if (cart.get(itemID) == null) {
			if (idao != null) {
				boolean storageResult = idao.checkStorage(itemID, oi.getQty());
				if (storageResult == false) {
					return false;
				}
			}
			cart.put(itemID, oi);
		} else {
			// 如果客戶在伺服器端已有此項商品的資料，則客戶『加購』此項商品
			OrderItemBean oib = cart.get(itemID);
			// 加購的數量：oi.getQty()
			// 原有的數量：oib.getQty()
			try {
				idao = new item_DAO();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			if (idao != null) {
				boolean storageResult = idao.checkStorage(itemID, oi.getQty() + oib.getQty());
				if (storageResult == false) {
					return false;
				}
			}
			oib.setQty(oi.getQty() + oib.getQty());
		}

		return true;
	}

	// 修改商品的數量
	public boolean modifyQty(int itemID, OrderItemBean oi) {
		if (cart.get(itemID) != null && oi.getQty() > 0) {
			cart.put(itemID, oi);
			return true;
		} else {
			return false;
		}
	}

	public boolean modifyQty(int itemID, int newQty) {
		if (cart.get(itemID) != null) {
			item_DAO idao = null;
			try {
				idao = new item_DAO();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			boolean storageResult = idao.checkStorage(itemID, newQty);
			if (storageResult == false) {
				return false;
			}
			OrderItemBean oi = cart.get(itemID);
			oi.setQty(newQty);
			// cart.put(itemID, oi);
			return true;
		} else {
			return false;
		}
	}

	// 刪除某項商品
	public int deleteBook(int itemID) {
		if (cart.get(itemID) != null) {
			cart.remove(itemID); // Map介面的remove()方法
			return 1;
		} else {
			return 0;
		}
	}

	public int getItemNumber() { // ShoppingCart.itemNumber
		return cart.size();
	}

	// 計算購物車內所有商品的合計金額(每項商品的單價*數量的總和)
	public double getSubtotal() {
		double subTotal = 0;
		Set<Integer> set = cart.keySet();
		for (int n : set) {
			double price = cart.get(n).getUnitPrice();
			double discount = cart.get(n).getDiscount();
			int qty = cart.get(n).getQty();
			subTotal += price * discount * qty;
		}
		return subTotal;
	}

	public void listCart() {
		Set<Integer> set = cart.keySet();
		for (Integer k : set) {
			System.out.printf("itemID=%3d,  Qty=%3d,  price=%5.2f,  discount=%6.2f\n", k, cart.get(k).getQty(),
					cart.get(k).getUnitPrice(), cart.get(k).getDiscount());
		}
		System.out.println("------------------");
	}
}
