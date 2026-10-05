'use client';

import React, { useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { 
  GraduationCap, 
  Lock, 
  User as UserIcon, 
  Eye, 
  EyeOff, 
  ArrowRight, 
  AlertCircle,
  Sparkles
} from 'lucide-react';
import { useAppDispatch } from '@/redux/hooks';
import { setCredentials } from '@/redux/slices/authSlice';
import { authService } from '@/services/authService';
import styles from './login.module.css';

export default function LoginPage() {
  const router = useRouter();
  const dispatch = useAppDispatch();

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!username.trim() || !password.trim()) {
      setErrorMessage('Please enter both username and password.');
      return;
    }

    setIsLoading(true);
    setErrorMessage(null);

    try {
      const response = await authService.login({ username, password });
      
      // Update Redux state and localStorage
      dispatch(
        setCredentials({
          user: {
            id: response.id,
            username: response.username,
            email: response.email,
            role: response.role,
          },
          token: response.token,
        })
      );

      // Redirect to home or role-specific dashboard
      router.push('/');
    } catch (err: unknown) {
      if (err && typeof err === 'object' && 'response' in err) {
        const axiosErr = err as { response?: { data?: string | { message?: string } } };
        const data = axiosErr.response?.data;
        if (typeof data === 'string') {
          setErrorMessage(data);
        } else if (data?.message) {
          setErrorMessage(data.message);
        } else {
          setErrorMessage('Invalid username or password. Please try again.');
        }
      } else {
        setErrorMessage('Unable to connect to Authentication Service. Please verify services are running.');
      }
    } finally {
      setIsLoading(false);
    }
  };

  const handleDemoFill = (demoUser: string, demoPass: string) => {
    setUsername(demoUser);
    setPassword(demoPass);
    setErrorMessage(null);
  };

  return (
    <div className={styles.container}>
      <div className={styles.ambientGlowTop} />
      <div className={styles.ambientGlowBottom} />

      <div className={styles.card}>
        <div className={styles.header}>
          <div className={styles.brandBadge}>
            <Sparkles size={14} />
            <span>AI University System</span>
          </div>
          <h1 className={styles.title}>
            Welcome <span className={styles.titleGradient}>Back</span>
          </h1>
          <p className={styles.subtitle}>
            Sign in to access your intelligent academic portal
          </p>
        </div>

        <form onSubmit={handleSubmit} className={styles.form}>
          {errorMessage && (
            <div className={styles.errorBox}>
              <AlertCircle size={16} />
              <span>{errorMessage}</span>
            </div>
          )}

          <div className={styles.fieldGroup}>
            <label htmlFor="username" className={styles.label}>
              Username
            </label>
            <div className={styles.inputWrapper}>
              <UserIcon size={18} className={styles.inputIcon} />
              <input
                id="username"
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                placeholder="e.g. admin or student01"
                className={styles.input}
                disabled={isLoading}
                required
                autoComplete="username"
              />
            </div>
          </div>

          <div className={styles.fieldGroup}>
            <label htmlFor="password" className={styles.label}>
              Password
            </label>
            <div className={styles.inputWrapper}>
              <Lock size={18} className={styles.inputIcon} />
              <input
                id="password"
                type={showPassword ? 'text' : 'password'}
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="••••••••"
                className={`${styles.input} ${styles.inputWithToggle}`}
                disabled={isLoading}
                required
                autoComplete="current-password"
              />
              <button
                type="button"
                onClick={() => setShowPassword(!showPassword)}
                className={styles.passwordToggle}
                tabIndex={-1}
                aria-label={showPassword ? 'Hide password' : 'Show password'}
              >
                {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
              </button>
            </div>
          </div>

          <button
            type="submit"
            className={styles.submitBtn}
            disabled={isLoading}
          >
            {isLoading ? (
              <>
                <span className={styles.spinner} />
                <span>Signing In...</span>
              </>
            ) : (
              <>
                <span>Sign In to Portal</span>
                <ArrowRight size={18} />
              </>
            )}
          </button>
        </form>

        <div className={styles.demoSection}>
          <div className={styles.demoLabel}>Quick Test Credentials</div>
          <div className={styles.demoBadges}>
            <button
              type="button"
              onClick={() => handleDemoFill('admin', 'admin123')}
              className={styles.demoBadge}
            >
              Admin
            </button>
            <button
              type="button"
              onClick={() => handleDemoFill('lecturer', 'lecturer123')}
              className={styles.demoBadge}
            >
              Lecturer
            </button>
            <button
              type="button"
              onClick={() => handleDemoFill('student', 'student123')}
              className={styles.demoBadge}
            >
              Student
            </button>
          </div>
        </div>

        <div className={styles.footer}>
          Don&apos;t have an account yet?
          <Link href="/register" className={styles.footerLink}>
            Register Here
          </Link>
        </div>
      </div>
    </div>
  );
}
