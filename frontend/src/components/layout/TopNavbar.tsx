'use client';

import React from 'react';
import { Search, Bell, Menu, Sparkles } from 'lucide-react';
import styles from './topnavbar.module.css';

interface TopNavbarProps {
  onToggleMobileMenu: () => void;
}

export default function TopNavbar({ onToggleMobileMenu }: TopNavbarProps) {
  return (
    <header className={styles.navbar}>
      <div className={styles.leftSection}>
        <button
          onClick={onToggleMobileMenu}
          className={styles.mobileMenuBtn}
          aria-label="Open navigation menu"
        >
          <Menu size={20} />
        </button>

        <div className={styles.searchBox}>
          <Search size={16} className={styles.searchIcon} />
          <input
            type="text"
            placeholder="Search students, courses, faculty..."
            className={styles.searchInput}
          />
          <kbd className={styles.searchKbd}>⌘K</kbd>
        </div>
      </div>

      <div className={styles.rightSection}>
        {/* Microservices Cluster Health */}
        <div className={styles.statusPill}>
          <span className={styles.statusDot} />
          <span>Services Online (8761, 8080, 8081, 8082)</span>
        </div>

        {/* Notifications */}
        <button className={styles.iconBtn} aria-label="Notifications" title="Notifications">
          <Bell size={18} />
          <span className={styles.notifBadge} />
        </button>

        {/* AI Quick Trigger */}
        <button className={styles.iconBtn} aria-label="AI Assistant" title="AI Assistant">
          <Sparkles size={18} style={{ color: '#38bdf8' }} />
        </button>
      </div>
    </header>
  );
}
