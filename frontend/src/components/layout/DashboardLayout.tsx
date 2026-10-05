'use client';

import React, { useState } from 'react';
import Sidebar from './Sidebar';
import TopNavbar from './TopNavbar';
import styles from './dashboard.module.css';

interface DashboardLayoutProps {
  children: React.ReactNode;
}

export default function DashboardLayout({ children }: DashboardLayoutProps) {
  const [collapsed, setCollapsed] = useState(false);
  const [mobileOpen, setMobileOpen] = useState(false);

  return (
    <div className={styles.layoutWrapper}>
      <div className={styles.ambientBackground} />

      {/* Mobile Drawer Backdrop */}
      {mobileOpen && (
        <div
          className={styles.mobileBackdrop}
          onClick={() => setMobileOpen(false)}
        />
      )}

      {/* Sidebar Navigation */}
      <Sidebar
        collapsed={collapsed}
        setCollapsed={setCollapsed}
        mobileOpen={mobileOpen}
        setMobileOpen={setMobileOpen}
      />

      {/* Main Content Area */}
      <div
        className={`${styles.mainWrapper} ${
          collapsed ? styles.mainWrapperCollapsed : ''
        }`}
      >
        <TopNavbar onToggleMobileMenu={() => setMobileOpen(!mobileOpen)} />
        <main className={styles.pageContent}>{children}</main>
      </div>
    </div>
  );
}
