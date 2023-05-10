import React from 'react';
import CheckboxContext from './CheckboxContext';

function Checkbox({ children, disabled, value, checked, onChange }: any) {
  const context = React.useContext(CheckboxContext);

  if (!context) {
    return (
      <label>
        <input
          type="checkbox"
          disabled={disabled}
          checked={checked}
          onChange={({ target: { checked } }) => onChange(checked)}
        />
        {children}
      </label>
    );
  }

  const { isDisabled, isChecked, toggleValue }: any = context;

  return (
    <label>
      <input
        type="checkbox"
        disabled={isDisabled(disabled)}
        checked={isChecked(value)}
        onChange={({ target: { checked } }) => toggleValue({ checked, value })}
      />
      {children}
    </label>
  );
}
